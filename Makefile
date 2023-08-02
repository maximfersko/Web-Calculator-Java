CXX = g++ -std=c++17
DIR_SOURCE_CORE = src/main/core
DIR_SOURCE_CALCULATOR = src/main/java/com/edu/fersko/smartcalc/models/wrapperCore/core
SMART_CALCULATOR = smartCalculator
CREDIT_CALCULATOR = creditCalculator
DIR_SOURCE_WRAPPER_CORE = src/main/java/com/edu/fersko/smartcalc/models/wrapperCore
WRAPPER_SMART_CALCULATOR_FILE = com_edu_fersko_smartcalc_models_SmartCalcJNIWrapper
WRAPPER_CREDIT_CALCULATOR_FILE = com_edu_fersko_smartcalc_models_CreditModelJNIWrapper
SOURCE = $(wildcard $(DIR_SOURCE_CORE)/*.cc) $(wildcard $(DIR_SOURCE_CALCULATOR)/*.cc)
JNI_INCLUDE_PATH = "C:\Program Files\Java\jdk-17\include"
JNI_INCLUDE_PATH_MACOS = "$(JAVA_HOME)/Include"
JNI_INCLUDE_PATH_MACOS_MD = "$(JNI_INCLUDE_PATH_MACOS)/Darwin"
JNI_INCLUDE_PATH_WIN = $(JNI_INCLUDE_PATH)\win32
FLAGS = -shared
LIB_SMART_NAME = SmartCore
LIB_CREDIT_NAME = CreditCore
PATH_LIB = src/main/java/com/edu/fersko/smartcalc/models/lib


windows:
	$(CXX) $(FLAGS) -I$(JNI_INCLUDE_PATH_WIN) -I$(JNI_INCLUDE_PATH) -o $(PATH_LIB)/$(LIB_SMART_NAME).dll \
															$(DIR_SOURCE_CALCULATOR)/$(SMART_CALCULATOR)/*.cc \
															$(DIR_SOURCE_WRAPPER_CORE)/$(WRAPPER_SMART_CALCULATOR_FILE).cc
	$(CXX) $(FLAGS) -I$(JNI_INCLUDE_PATH_WIN) -I$(JNI_INCLUDE_PATH) -o $(PATH_LIB)/$(LIB_CREDIT_NAME).dll \
															$(DIR_SOURCE_CALCULATOR)/$(CREDIT_CALCULATOR)/*.cc \
															$(DIR_SOURCE_WRAPPER_CORE)/$(WRAPPER_CREDIT_CALCULATOR_FILE).cc


macos:
	$(CXX) $(FLAGS) -I$(JNI_INCLUDE_PATH_MACOS) -I$(JNI_INCLUDE_PATH_MACOS_MD) -o \
														$(PATH_LIB)/$(LIB_NAME).so \
														$(SOURCE) \
							                            $(DIR_SOURCE_WRAPPER_CORE)/$(WRAPPER_CORE_FILE).cc