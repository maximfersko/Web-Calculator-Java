#include "smartCalc.h"

namespace model {

typename SmartCalc::data SmartCalc::RPN(std::string str, data X) {
  return ComputePolishNotation(str, X);
}

void SmartCalc::ComputeBinary(int token) {
  data resultForCalcBinar = 0;
  auto [first, _] = result_.back();
  result_.pop_back();
  auto [second, __] = result_.back();
  result_.pop_back();
  switch (token) {
  case PLUS:
    resultForCalcBinar = second + first;
    break;
  case MINUS:
    resultForCalcBinar = second - first;
    break;
  case DIV:
    if (first == 0)
      Error("You can't divide by zero");
    resultForCalcBinar = second / first;
    break;
  case MULT:
    resultForCalcBinar = first * second;
    break;
  case MOD:
    resultForCalcBinar = fmod(second, first);
    break;
  case POW:
    resultForCalcBinar = pow(second, first);
    break;
  }
  result_.push_back({resultForCalcBinar, NUMBER});
}

void SmartCalc::ComputeUnary(int token) {
  auto [first, _] = result_.back();
  result_.pop_back();
  data resultForComputeUnary = 0;
  static const std::map<int, func_ptr> unary_functions = {
      {COS, cos},
      {SIN, sin},
      {TAN, tan},
      {ACOS, acos},
      {ASIN, asin},
      {ATAN, atan},
      {SQRT, sqrt},
      {LN, log},
      {LOG, log10},
      {UMINUS, [](double x) { return -x; }},
      {UPLUS, [](double x) { return x; }},
  };
  auto it = unary_functions.find(token);
  if (it != unary_functions.end()) {
    resultForComputeUnary = it->second(first);
  } else {
    throw std::out_of_range("Out of range !");
  }
  result_.push_back(std::make_pair(resultForComputeUnary, NUMBER));
}

SmartCalc::data SmartCalc::ComputePolishNotation(std::string &str, data x) {
  PolishNotation(str);
  std::deque<std::pair<double, int>> items(numbers_);
  for (auto &item : items) {
    if (item.second == NUMBER) {
      result_.push_back(std::make_pair(item.first, NUMBER));
    } else if (item.first >= PLUS && item.first <= MOD) {
      if (result_.size() < 2)
        Error("Error calculation !");
      ComputeBinary(item.first);
    } else if (item.first >= COS && item.first <= LN) {
      if (result_.size() < 1)
        Error("Error calculation !");
      ComputeUnary(item.first);
    } else if (item.second == X) {
      result_.push_back(std::make_pair(x, NUMBER));
    }
  }
  numbers_.clear();
  data result = 0;
  if (result_.size() > 1 || result_.empty())
    Error("Error calculation !");
  result = result_.back().first;
  clean();
  return result;
}

void SmartCalc::clean() {
  numbers_.clear();
  operations_.clear();
  result_.clear();
  it_ = 0;
}

}; // namespace model
