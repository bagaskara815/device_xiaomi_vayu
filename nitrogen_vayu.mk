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

# Inherit common Nitrogen configurations
$(call inherit-product, vendor/nitrogen/products/common.mk)

# Gapps
$(call inherit-product, vendor/google/gms/config.mk)

PRODUCT_NAME := nitrogen_vayu
PRODUCT_DEVICE := vayu
PRODUCT_BRAND := POCO
PRODUCT_MODEL := Poco X3 Pro
PRODUCT_MANUFACTURER := Xiaomi

# Boot animation
TARGET_SCREEN_HEIGHT := 2400
TARGET_SCREEN_WIDTH := 1080

PRODUCT_SYSTEM_NAME := vayu
PRODUCT_SYSTEM_DEVICE := vayu

TARGET_NOT_USES_BLUR=true

PRODUCT_GMS_CLIENTID_BASE := android-xiaomi

PRODUCT_BUILD_PROP_OVERRIDES += \
    PRIVATE_BUILD_DESC="vayu_id-user 13 TKQ1.221013.002 V14.0.3.0.TJUIDXM release-keys"
    TARGET_DEVICE="vayu" \
    TARGET_PRODUCT="vayu"

BUILD_FINGERPRINT := Xiaomi/vayu_global/vayu:13/TKQ1.221013.002/V14.0.1.0.TJUMIXM:user/release-keys
