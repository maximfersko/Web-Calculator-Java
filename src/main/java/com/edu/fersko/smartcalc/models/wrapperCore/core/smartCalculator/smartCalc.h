#ifndef SRC_MODEL_SMARTCALC_H_
#define SRC_MODEL_SMARTCALC_H_

#include <cmath>
#include <cstring>
#include <deque>
#include <iostream>
#include <limits>
#include <map>
#include <unordered_map>
#include <vector>

namespace model {

#ifndef M_PI
    #define M_PI 3.14159265358979323846
#endif

#ifndef M_E
    #define M_E 2.71828182845904523536
#endif

enum {
  PLUS,
  MINUS,
  DIV,
  MULT,
  POW,
  MOD,
  COS,
  SIN,
  TAN,
  ATAN,
  ACOS,
  ASIN,
  SQRT,
  UPLUS,
  UMINUS,
  LOG,
  LN,
  X,
  DOT,
  CLOSE_BRACKET = -1,
  OPEN_BRACKET = -2,
  null
};

enum { CONST_E = 19, CONST_PI, NUMBER, UNDEFUIEND, OPERATION, EMPTY };

enum { LOW = 1, MID, HIGH, VHIGH, VVHIGH };

class SmartCalc {
public:
  using data = double;
  using operation = std::deque<std::pair<int, int>>;
  using number = std::deque<std::pair<data, int>>;
  using func_ptr = double (*)(double);

private:
  const std::unordered_map<std::string, int> elements_{
      {"+", PLUS}, {"-", MINUS},   {"/", DIV},          {"*", MULT},
      {"%", MOD},  {"^", POW},     {"(", OPEN_BRACKET}, {")", CLOSE_BRACKET},
      {"c", COS},  {"s", SIN},     {"a", ACOS},         {"l", LN},
      {"x", X},    {"e", CONST_E}, {"p", CONST_PI},     {"t", TAN},
      {".", DOT}};

private:
  operation operations_;
  number numbers_;
  number result_;
  size_t it_ = 0;

  void AddOperation(int token);
  void AddBrackets();
  void TransferData();
  void ValidateBrackets(std::string &str);
  int ExtractTokenFromStr(std::string &str, bool *flag);
  void PolishNotation(std::string &str);
  data ExtractNumFromStr(std::string &str, data value, size_t *length);
  int GetPriority(int operation);
  void ComputeBinary(int token);
  void ComputeUnary(int token);
  int IsUnarOrBinar(int type, bool flag);
  int GetTypeFunction(std::string &str, int type);

  void Validate(const number &A);
  void ShiftIterator(int token);
  void Error(std::string str);

public:
  SmartCalc() = default;
  ~SmartCalc() = default;
  void clean();
  data RPN(std::string str, data X = {});
    data ComputePolishNotation(std::string &str, data x = {});
  std::pair<std::vector<double>, std::vector<double>>
  graphBuilder(std::vector<double> &Data, std::string str);
};

}; // namespace model
#endif
