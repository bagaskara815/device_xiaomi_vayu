LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := RemovePackagesVayu
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_TAGS := optional
LOCAL_OVERRIDES_PACKAGES := \
    Accord \
    Calculator2 \
    Chrome64 \
    Chrome \
    Chrome-Stub \
    ConnMO \
    CredentialManagerPrebuilt_credentialmanager.android_20250404.06_p0 \
    Drive \
    DCMO \
    Etar \
    ExactCalculator \
    FilesGoogle \
    FilesPrebuilt  \
    Flipendo \
	FossifyGallery \
    GeminiShell_227 \
    Gramophone \
    Jelly \
    Maestro \
    Maps \
    Panic \
    PersonalSafety \
    Recorder \
    RecorderPrebuilt \
    Ripple \
    SafetyHubPrebuilt \
    ScribePrebuilt_v8.4.773573318 \
    Symphonica \
    SwitchAccessPrebuilt_1.16.0.726766860 \
    TurboAdapter \
    Twelve \
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
