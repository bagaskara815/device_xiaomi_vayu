#!/usr/bin/env -S PYTHONPATH=../../../tools/extract-utils python3
#
# SPDX-FileCopyrightText: 2024 The LineageOS Project
# SPDX-License-Identifier: Apache-2.0
#

from extract_utils.fixups_blob import (
    blob_fixup,
    blob_fixups_user_type,
)
from extract_utils.fixups_lib import (
    lib_fixups,
    lib_fixups_user_type,
)
from extract_utils.main import (
    ExtractUtils,
    ExtractUtilsModule,
)

namespace_imports = [
    'hardware/qcom-caf/sm8150',
    'device/xiaomi/vayu',
    'hardware/xiaomi',
    'hardware/qcom-caf/common/libqti-perfd-client',
    'vendor/qcom/opensource/display',
	'hardware/qcom-caf/wlan',
	'vendor/qcom/opensource/commonsys-intf/display',
	'vendor/qcom/opensource/commonsys/display',
	'vendor/qcom/opensource/dataservices',
]

lib_fixups: lib_fixups_user_type = {
    **lib_fixups,
}

def lib_fixup_vendor_suffix(lib: str, partition: str, *args, **kwargs):
    return f'{lib}_{partition}' if partition == 'vendor' else None


lib_fixups: lib_fixups_user_type = {
    **lib_fixups,
    (
        'com.qualcomm.qti.dpm.api@1.0',
        'libmmosal',
        'vendor.qti.hardware.fm@1.0',
        'vendor.qti.imsrtpservice@3.0',
    ): lib_fixup_vendor_suffix,
}


blob_fixups: blob_fixups_user_type = {
    'vendor/lib64/camera/components/com.qti.node.watermark.so': blob_fixup()
        .add_needed('libpiex_shim.so'),
    'vendor/etc/init/init.batterysecret.rc': blob_fixup()
        .regex_replace(' +seclabel u:r:batterysecret:s0\n', ''),
    'vendor/lib/libaudioroute_ext.so': blob_fixup()
        .replace_needed('libaudioroute.so', 'libaudioroute-v34.so'),
    'vendor/lib/hw/audio.primary.vayu.so': blob_fixup()
        .binary_regex_replace(
            b'/vendor/lib/liba2dpoffload.so',
            b'liba2dpoffload_vayu.so\x00\x00\x00\x00\x00\x00\x00',
        )
        .replace_needed('libaudioroute.so', 'libaudioroute-v34.so'),
    ('vendor/lib64/libalLDC.so', 'vendor/lib64/libalAILDC.so', 'vendor/lib64/libalhLDC.so'): blob_fixup()
        .clear_symbol_version('AHardwareBuffer_allocate')
        .clear_symbol_version('AHardwareBuffer_describe')
        .clear_symbol_version('AHardwareBuffer_lock')
        .clear_symbol_version('AHardwareBuffer_release')
        .clear_symbol_version('AHardwareBuffer_unlock'),
    'vendor/lib64/hw/camera.qcom.so': blob_fixup()
        .binary_regex_replace(
            b'\x73\x74\x5F\x6C\x69\x63\x65\x6E\x73\x65\x2E\x6C\x69\x63',
            b'\x63\x61\x6D\x65\x72\x61\x5F\x63\x6E\x66\x2E\x74\x78\x74',
        ),
    'vendor/etc/init/init.mi_thermald.rc': blob_fixup()
        .regex_replace(r'seclabel u:r:mi_thermald:s0', ''),
    (
        'vendor/lib/libstagefright_soft_ac4dec.so',
        'vendor/lib/libstagefright_soft_ddpdec.so',
        'vendor/lib/libstagefrightdolby.so',
        'vendor/lib64/libdlbdsservice.so',
        'vendor/lib64/libstagefright_soft_ac4dec.so',
        'vendor/lib64/libstagefright_soft_ddpdec.so',
        'vendor/lib64/libstagefrightdolby.so',
    ): blob_fixup()
        .replace_needed('libstagefright_foundation.so', 'libstagefright_foundation-v33.so'),
    'vendor/lib64/libdpps.so': blob_fixup()
        .replace_needed('libtinyxml2.so', 'libtinyxml2-v34.so'),
    'vendor/lib64/libwvhidl.so': blob_fixup()
        .add_needed('libcrypto_shim.so'),
    'vendor/lib64/mediadrm/libwvdrmengine.so': blob_fixup()
        .add_needed('libcrypto_shim.so'),
    ('vendor/etc/init/android.hardware.drm@1.3-service.widevine.rc', 'vendor/etc/init/vendor.qti.media.c2@1.0-service.rc'): blob_fixup()
        .regex_replace(r'writepid.*', 'task_profiles ProcessCapacityHigh HighPerformance'),
    'vendor/etc/init/android.hardware.neuralnetworks@1.3-service-qti.rc': blob_fixup()
        .regex_replace(r'writepid.*', 'task_profiles NNApiHALPerformance'),
}  # fmt: skip

module = ExtractUtilsModule(
    'vayu',
    'xiaomi',
    blob_fixups=blob_fixups,
    lib_fixups=lib_fixups,
    namespace_imports=namespace_imports,
)

if __name__ == '__main__':
    utils = ExtractUtils.device(module)
    utils.run()
