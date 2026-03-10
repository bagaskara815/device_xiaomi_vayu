#!/usr/bin/env -S PYTHONPATH=../../../tools/extract-utils python3
#
# SPDX-FileCopyrightText: The LineageOS Project
# SPDX-License-Identifier: Apache-2.0
#

from extract_utils.file import File
from extract_utils.fixups_blob import (
    BlobFixupCtx,
    blob_fixup,
    blob_fixups_user_type,
)
from extract_utils.fixups_lib import (
    lib_fixup_remove,
    lib_fixups,
    lib_fixups_user_type,
)
from extract_utils.main import (
    ExtractUtils,
    ExtractUtilsModule,
)

namespace_imports = [
    'device/xiaomi/vayu',
    'hardware/qcom-caf/common/libqti-perfd-client',
    'hardware/qcom-caf/sm8150',
    'hardware/qcom-caf/wlan',
    'hardware/xiaomi',
    'vendor/qcom/opensource/commonsys-intf/display',
    'vendor/qcom/opensource/commonsys/display',
    'vendor/qcom/opensource/dataservices',
    'vendor/qcom/opensource/display',
]


def lib_fixup_vendor_suffix(lib: str, partition: str, *args, **kwargs):
    return f'{lib}_{partition}' if partition == 'vendor' else None


lib_fixups: lib_fixups_user_type = {
    **lib_fixups,
    (
        'com.qualcomm.qti.dpm.api@1.0',
        'libmmosal',
        'vendor.qti.hardware.fm@1.0',
        'vendor.qti.hardware.wifidisplaysession@1.0',
        'vendor.qti.imsrtpservice@3.0',
    ): lib_fixup_vendor_suffix,
    (
        'libOmxCore',
        'libgrallocutils',
        'libwpa_client',
    ): lib_fixup_remove,
}

blob_fixups: blob_fixups_user_type = {
    (
        'vendor/etc/qdcm_calib_data_xiaomi_36_02_0a_video_mode_dsc_dsi_panel.xml',
        'vendor/etc/qdcm_calib_data_xiaomi_42_02_0b_video_mode_dsc_dsi_panel.xml',
    ): blob_fixup()
        .regex_replace('dcip3', 'srgb'),
    'vendor/bin/mi_thermald': blob_fixup()
        .binary_regex_replace(b'%d/on', b'%d/..'),
    'vendor/lib64/hw/camera.qcom.so': blob_fixup()
        .remove_needed('libMegviiFacepp-0.5.2.so')
        .remove_needed('libmegface.so')
        .add_needed('libshim_megvii.so'),
    'vendor/lib64/camera/components/com.qti.node.watermark.so': blob_fixup()
        .add_needed('libpiex_shim.so'),
    'vendor/etc/seccomp_policy/vendor.qti.hardware.dsp.policy': blob_fixup()
        .add_line_if_missing('madvise: 1'),
    (
        'vendor/lib/libwvhidl.so',
        'vendor/lib/mediadrm/libwvdrmengine.so',
        'vendor/lib64/libwvhidl.so',
        'vendor/lib64/mediadrm/libwvdrmengine.so',
    ): blob_fixup()
        .replace_needed('libcrypto.so', 'libcrypto-v33.so'),
    (
        'vendor/lib/libstagefright_soft_ddpdec.so',
        'vendor/lib/libstagefright_soft_ac4dec.so',
        'vendor/lib/libstagefrightdolby.so',
        'vendor/lib64/libstagefright_soft_ddpdec.so',
        'vendor/lib64/libdlbdsservice.so',
        'vendor/lib64/libstagefright_soft_ac4dec.so',
        'vendor/lib64/libstagefrightdolby.so',
    ): blob_fixup()
        .replace_needed('libstagefright_foundation.so', 'libstagefright_foundation-v33.so'),
    (
        'vendor/lib/libaudioroute_ext.so',
        'vendor/lib/hw/audio.primary.vayu.so',
    ): blob_fixup()
        .replace_needed('libaudioroute.so', 'libaudioroute-v34.so'),
}  # fmt: skip

module = ExtractUtilsModule(
    'vayu',
    'xiaomi',
    blob_fixups=blob_fixups,
    lib_fixups=lib_fixups,
    namespace_imports=namespace_imports,
)

# module.add_proprietary_file('proprietary-files.txt')

if __name__ == '__main__':
    utils = ExtractUtils.device(module)
    utils.run()
