LOCAL_PATH:=$(call my-dir)


include $(CLEAR_VARS)


LOCAL_MODULE := keypad
LOCAL_SRC_FILES := keypad.c 

include $(BUILD_SHARED_LIBRARY)


include $(CLEAR_VARS)


LOCAL_MODULE := dipsw
LOCAL_SRC_FILES := dipsw.c

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)


LOCAL_MODULE    := textlcd
LOCAL_SRC_FILES := textlcd.c

include $(BUILD_SHARED_LIBRARY)


