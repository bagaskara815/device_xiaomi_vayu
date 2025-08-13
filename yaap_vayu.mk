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

# Inherit common yaap configurations
$(call inherit-product, vendor/yaap/config/common_full_phone.mk)

# Gapps
TARGET_BUILD_GAPPS := true

# Boot animation
scr_resolution := 1080
TARGET_SCREEN_HEIGHT := 2400
TARGET_SCREEN_WIDTH := 1080

PRODUCT_NAME := yaap_vayu
PRODUCT_DEVICE := vayu
PRODUCT_BRAND := POCO
PRODUCT_MODEL := Poco X3 Pro
PRODUCT_MANUFACTURER := Xiaomi

PRODUCT_GMS_CLIENTID_BASE := android-xiaomi

PRODUCT_BUILD_PROP_OVERRIDES += \
    BuildDesc="vayu_id-user 13 TKQ1.221013.002 V14.0.3.0.TJUIDXM release-keys" \
    BuildFingerprint=POCO/vayu_id/vayu:13/TKQ1.221013.002/V14.0.3.0.TJUIDXM:user/release-keys \
    DeviceName=vayu \
    DeviceProduct=vayu \
    SystemDevice=vayu \
    SystemName=vayu
