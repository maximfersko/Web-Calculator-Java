CXX                            = g++ -std=c++17
DIR_SOURCE_CORE                = src/main/core
DIR_SOURCE_CALCULATOR          = src/main/java/com/edu/fersko/smartcalc/models/wrapperCore/core
SMART_CALCULATOR               = smartCalculator
CREDIT_CALCULATOR              = creditCalculator
DIR_SOURCE_WRAPPER_CORE        = src/main/java/com/edu/fersko/smartcalc/models/wrapperCore
WRAPPER_SMART_CALCULATOR_FILE  = com_edu_fersko_smartcalc_models_SmartCalcJNIWrapper
WRAPPER_CREDIT_CALCULATOR_FILE = com_edu_fersko_smartcalc_models_CreditModelJNIWrapper
JNI_INCLUDE_PATH               = "C:\Program Files\Java\jdk-17\include"
JNI_INCLUDE_PATH_MACOS         = "$(JAVA_HOME)/Include"
JNI_INCLUDE_PATH_MACOS_MD      = "$(JNI_INCLUDE_PATH_MACOS)/Darwin"
JNI_INCLUDE_PATH_WIN           = $(JNI_INCLUDE_PATH)\win32
FLAGS                          = -shared
LIB_SMART_NAME                 = SmartCore
LIB_CREDIT_NAME                = CreditCore
PATH_LIB                       = src/main/java/com/edu/fersko/smartcalc/models/lib

.PHONY: clean

default: clean lib

ifeq ($(OS),Windows_NT)
    LIB_EXT = dll
    JNI_INCLUDE = $(JNI_INCLUDE_PATH)
    JNI_INCLUDE_MD = $(JNI_INCLUDE_PATH_WIN)
    RM_CMD = Remove-Item
else
    UNAME_S := $(shell uname -s)
    ifeq ($(UNAME_S),Darwin)
        LIB_EXT = so
        JNI_INCLUDE = $(JNI_INCLUDE_PATH_MACOS)
        JNI_INCLUDE_MD = $(JNI_INCLUDE_PATH_MACOS_MD)
        RM_CMD = rm -f
    else
        $(error Unsupported operating system)
    endif
endif

JNI = -I$(JNI_INCLUDE) -I$(JNI_INCLUDE_MD)

lib:
	$(CXX) $(FLAGS) $(JNI) -o $(PATH_LIB)/$(LIB_SMART_NAME).$(LIB_EXT) \
														$(DIR_SOURCE_CALCULATOR)/$(SMART_CALCULATOR)/*.cc \
														$(DIR_SOURCE_WRAPPER_CORE)/$(WRAPPER_SMART_CALCULATOR_FILE).cc
	$(CXX) $(FLAGS) $(JNI) -o $(PATH_LIB)/$(LIB_CREDIT_NAME).$(LIB_EXT) \
														$(DIR_SOURCE_CALCULATOR)/$(CREDIT_CALCULATOR)/*.cc \
														$(DIR_SOURCE_WRAPPER_CORE)/$(WRAPPER_CREDIT_CALCULATOR_FILE).cc

clean:
	$(RM_CMD) $(PATH_LIB)/*.so $(PATH_LIB)/*.dll