CXX                            = g++ -std=c++17
SRC_CALCULATOR                 = src/main/java/com/edu/fersko/smartcalc/models/wrapperCore/core
SRC_WRAPPER_CORE               = src/main/java/com/edu/fersko/smartcalc/models/wrapperCore
WRAPPER_SMART_CALCULATOR_FILE  = com_edu_fersko_smartcalc_models_SmartCalcJNIWrapper
WRAPPER_CREDIT_CALCULATOR_FILE = com_edu_fersko_smartcalc_models_CreditModelJNIWrapper
PATH_LIB                       = src/main/java/com/edu/fersko/smartcalc/models/lib
SMART_CALCULATOR               = smartCalculator
CREDIT_CALCULATOR              = creditCalculator
JNI_INCLUDE_PATH               = "C:\Program Files\Java\jdk-17\include"
JNI_INCLUDE_PATH_MACOS         = "$(JAVA_HOME)/Include"
JNI_INCLUDE_PATH_MACOS_MD      = "$(JNI_INCLUDE_PATH_MACOS)/Darwin"
JNI_INCLUDE_PATH_WIN           = $(JNI_INCLUDE_PATH)\win32
FLAGS                          = -shared
LIB_SMART_NAME                 = SmartCore
LIB_CREDIT_NAME                = CreditCore

ifeq ($(OS),Windows_NT)
    LIB_EXT = dll
    JNI_INCLUDE = $(JNI_INCLUDE_PATH)
    JNI_INCLUDE_MD = $(JNI_INCLUDE_PATH_WIN)
    RM = Remove-Item
else
    UNAME_S := $(shell uname -s)
    ifeq ($(UNAME_S),Darwin)
        LIB_EXT = so
        JNI_INCLUDE = $(JNI_INCLUDE_PATH_MACOS)
        JNI_INCLUDE_MD = $(JNI_INCLUDE_PATH_MACOS_MD)
        RM = rm -f
    else
        $(error Unsupported operating system)
    endif
endif

JNI = -I$(JNI_INCLUDE) -I$(JNI_INCLUDE_MD)

SRC_SMART = $(wildcard $(SRC_CALCULATOR)/$(SMART_CALCULATOR)/*.cc)
SRC_CREDIT = $(wildcard $(SRC_CALCULATOR)/$(CREDIT_CALCULATOR)/*.cc)
SRC_WRAPPERS = $(SRC_WRAPPER_CORE)/$(WRAPPER_SMART_CALCULATOR_FILE).cc $(SRC_WRAPPER_CORE)/$(WRAPPER_CREDIT_CALCULATOR_FILE).cc

.PHONY: clean lib

default: lib

all: lib

lib: $(PATH_LIB)/$(LIB_SMART_NAME).$(LIB_EXT) $(PATH_LIB)/$(LIB_CREDIT_NAME).$(LIB_EXT)

$(PATH_LIB)/$(LIB_SMART_NAME).$(LIB_EXT): $(SRC_SMART) $(SRC_WRAPPERS)
	$(CXX) $(FLAGS) $(JNI) -o $@ $(SRC_SMART) $(SRC_WRAPPER_CORE)/$(WRAPPER_SMART_CALCULATOR_FILE).cc

$(PATH_LIB)/$(LIB_CREDIT_NAME).$(LIB_EXT): $(SRC_CREDIT) $(SRC_WRAPPERS)
	$(CXX) $(FLAGS) $(JNI) -o $@ $(SRC_CREDIT) $(SRC_WRAPPER_CORE)/$(WRAPPER_CREDIT_CALCULATOR_FILE).cc

clean:
	$(RM) $(PATH_LIB)/$(LIB_SMART_NAME).$(LIB_EXT) $(PATH_LIB)/$(LIB_CREDIT_NAME).$(LIB_EXT)
