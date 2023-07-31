CXX = g++ -std=c++17
DIR_SOURCE_CORE = src/main/core
DIR_SOURCE_MAIN_CALCULATOR = src/main/java/com/edu/fersko/smartcalc/models/wrapperCore/core/smartCalculator
DIR_SOURCE_WRAPPER_CORE = src/main/java/com/edu/fersko/smartcalc/models/wrapperCore
WRAPPER_CORE_FILE = com_edu_fersko_smartcalc_models_RPN
SOURCE = $(wildcard $(DIR_SOURCE_CORE)/*.cc) $(wildcard $(DIR_SOURCE_MAIN_CALCULATOR)/*.cc)
JNI_INCLUDE_PATH = "C:\Program Files\Java\jdk-17\include"
JNI_INCLUDE_PATH_MACOS = "$(JAVA_HOME)/Include"
JNI_INCLUDE_PATH_MACOS_MD = "$(JNI_INCLUDE_PATH_MACOS)/Darwin"
JNI_INCLUDE_PATH_WIN = $(JNI_INCLUDE_PATH)\win32
FLAGS = -shared
LIB_NAME = core
PATH_LIB = src/main/java/com/edu/fersko/smartcalc/models/lib


windows:
	$(CXX) $(FLAGS) -I$(JNI_INCLUDE_PATH_WIN) -I$(JNI_INCLUDE_PATH) -o $(PATH_LIB)/$(LIB_NAME).dll \
															$(DIR_SOURCE_MAIN_CALCULATOR)/parser.cc \
															$(DIR_SOURCE_MAIN_CALCULATOR)/calculate.cc \
															$(DIR_SOURCE_MAIN_CALCULATOR)/graphBuilder.cc \
															$(DIR_SOURCE_MAIN_CALCULATOR)/validator.cc \
															$(DIR_SOURCE_WRAPPER_CORE)/$(WRAPPER_CORE_FILE).cc


macos:
	$(CXX) $(FLAGS) -I$(JNI_INCLUDE_PATH_MACOS) -I$(JNI_INCLUDE_PATH_MACOS_MD) -o \
														$(PATH_LIB)/$(LIB_NAME).so \
														$(SOURCE) \
							                            $(DIR_SOURCE_WRAPPER_CORE)/$(WRAPPER_CORE_FILE).cc