LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := RemovePackagesVayu
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_TAGS := optional
LOCAL_OVERRIDES_PACKAGES := \
    Accord \
    Chrome64 \
    Chrome \
    Chrome-Stub \
    ConnMO \
    CredentialManagerPrebuilt_credentialmanager.android_20250404.06_p0 \
    Drive \
    DCMO \
    FilesGoogle \
    FilesPrebuilt  \
    Flipendo \
	FossifyGallery \
    GeminiShell_227 \
    Gramophone \
    Maestro \
    Maps \
    Panic \
    PersonalSafety \
    RecorderPrebuilt \
    Ripple \
    SafetyHubPrebuilt \
    ScribePrebuilt_v8.4.773573318 \
    Symphonica \
    SwitchAccessPrebuilt_1.16.0.726766860 \
    TurboAdapter \
    Tycho \
    UdfpsAnimations \
    UdfpsIcons \
    VoiceAccessPrebuilt \
    VZWAPNLib \
    YouTube

LOCAL_UNINSTALLABLE_MODULE := true
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_SRC_FILES := /dev/null
include $(BUILD_PREBUILT)
