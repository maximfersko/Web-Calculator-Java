#include "smartCalc.h"

namespace model {

void SmartCalc::ValidateBrackets(std::string &str) {
  int isBrackets = 0;
  int i = 0;
  while (str[i]) {
    if (str[i] == '(' || str[i] == ')')
      ++isBrackets;
    ++i;
  }
  if (isBrackets % 2 != 0)
    Error("Error brackets !");
}

void SmartCalc::Validate(const number &A) {
  if (A.front().second == OPERATION)
    Error("Error RPN");
  if (!A.empty()) {
    int isOperation = 0, isNumber = 0;
    bool Unar = false;
    for (auto &elm : A) {
      if (elm.first >= PLUS && elm.first <= LN &&
          !((elm.second >= CONST_E && elm.second <= NUMBER) ||
            (elm.second == X))) {
        ++isOperation;
      }
      if ((elm.second >= CONST_E && elm.second <= NUMBER) ||
          (elm.second == X)) {
        ++isNumber;
      }
      if (elm.first >= COS && elm.first <= LN &&
          !((elm.second >= CONST_E && elm.second <= NUMBER) ||
            (elm.second == X))) {
        Unar = true;
      }
    }
    if ((isOperation == 0 && isNumber > 0) ||
        (isNumber == 0 && isOperation > 0))
      Error("Invalid expressions !");

    // TODO: need fix
    // if (!Unar) {
    //     if (isNumber % isOperation != 0)
    //         throw std::invalid_argument("invalid expressions2");
    // } else if (isNumber % isOperation == 0) {
    //     throw std::invalid_argument("invalid expressions3");
    // }
  }
}

}; // namespace model
