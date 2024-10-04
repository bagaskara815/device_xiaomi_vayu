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
    Drive \
    DCMO \
    FilesGoogle \
    FilesPrebuilt  \
    Gramophone \
    Maestro \
    Maps \
    PersonalSafety \
    RecorderPrebuilt \
    SafetyHubPrebuilt \
    Symphonica \
    Tycho \
    VoiceAccessPrebuilt \
    VZWAPNLib \
    YouTube

LOCAL_UNINSTALLABLE_MODULE := true
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_SRC_FILES := /dev/null
include $(BUILD_PREBUILT)
