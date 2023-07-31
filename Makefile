CXX = g++ -std=c++17
DIR_SOURCE_CORE = src/main/core
DIR_SOURCE_WRAPPER_CORE = src/main/java/com/edu/fersko/smartcalc/models/wrapperCore/core/smartCalculator
SOURCE = $(wildcard $(DIR_SOURCE_CORE)/*.cc) $(wildcard $(DIR_SOURCE_WRAPPER_CORE)/*.cc)
JNI_INCLUDE_PATH = "C:\Program Files\Java\jdk-17\include"
JNI_INCLUDE_PATH_WIN = $(JNI_INCLUDE_PATH)\win32
FLAGS = -shared
LIB_NAME = core
PATH_LIB = src/main/java/com/edu/fersko/smartcalc/models/lib

windows:
	$(CXX) $(FLAGS) -I$(JNI_INCLUDE_PATH_WIN) -I$(JNI_INCLUDE_PATH) -o $(PATH_LIB)/$(LIB_NAME).dll \
	$(DIR_SOURCE_WRAPPER_CORE)/parser.cc \
	$(DIR_SOURCE_WRAPPER_CORE)/calculate.cc \
	$(DIR_SOURCE_WRAPPER_CORE)/graphBuilder.cc \
	$(DIR_SOURCE_WRAPPER_CORE)/validator.cc \
	src/main/java/com/edu/fersko/smartcalc/models/wrapperCore/com_edu_fersko_smartcalc_models_RPN.cc