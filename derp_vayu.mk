#
# Copyright (C) 2018-2021 ArrowOS
#
# SPDX-License-Identifier: Apache-2.0
#

# Inherit common products
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base_telephony.mk)

# Inherit device configurations
$(call inherit-product, device/xiaomi/vayu/device.mk)

# Inherit common DerpFest configurations
$(call inherit-product, vendor/derp/config/common_full_phone.mk)

PRODUCT_NAME := derp_vayu
PRODUCT_DEVICE := vayu
PRODUCT_BRAND := POCO
PRODUCT_MODEL := Poco X3 Pro
PRODUCT_MANUFACTURER := Xiaomi

DERP_BUILDTYPE := Official
DERP_VERSION_APPEND_TIME_OF_DAY=true
TARGET_NOT_USES_BLUR=true
USE_LEGACY_BOOTANIMATION := true
TARGET_USES_MINI_GAPPS := true

PRODUCT_GMS_CLIENTID_BASE := android-xiaomi

PRODUCT_BUILD_PROP_OVERRIDES += \
    BuildDesc="vayu_id-user 13 TKQ1.221013.002 V14.0.3.0.TJUIDXM release-keys" \
    BuildFingerprint=POCO/vayu_id/vayu:13/TKQ1.221013.002/V14.0.3.0.TJUIDXM:user/release-keys \
    DeviceName=vayu \
    DeviceProduct=vayu \
    SystemDevice=vayu \
    SystemName=vayu
