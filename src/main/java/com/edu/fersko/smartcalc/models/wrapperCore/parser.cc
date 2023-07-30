#include <algorithm>
#include <array>

#include "smartCalc.h"

namespace model {

void SmartCalc::ShiftIterator(int token) {
  if (token != NUMBER)
    ++it_;
  if ((token >= COS && token <= TAN) || (token == LOG)) {
    it_ += 2;
  } else if (token >= ATAN && token <= SQRT) {
    it_ += 3;
  }
}

void SmartCalc::Error(std::string str) {
  clean();
  throw std::invalid_argument(str);
}

int SmartCalc::GetTypeFunction(std::string &str, int type) {
  int token = UNDEFUIEND;
  std::string sub = str.substr(it_);
  switch (type) {
  case COS:
    if (sub.substr(0, 3) == "cos")
      token = COS;
    break;
  case TAN:
    if (sub.substr(0, 3) == "tan")
      token = TAN;
    break;
  case SIN:
    if (sub.substr(0, 3) == "sin")
      token = SIN;
    else if (sub.substr(0, 4) == "sqrt")
      token = SQRT;
    break;
  case ACOS:
    if (sub.substr(0, 4) == "acos")
      token = ACOS;
    else if (sub.substr(0, 4) == "asin")
      token = ASIN;
    else if (sub.substr(0, 4) == "atan")
      token = ATAN;
    break;
  case LN:
    if (sub.substr(0, 2) == "ln")
      token = LN;
    else if (sub.substr(0, 3) == "log")
      token = LOG;
    break;
  }
  return token;
}

int SmartCalc::IsUnarOrBinar(int type, bool flag) {
  if (flag) {
    return (type == PLUS) ? UPLUS : (type == MINUS) ? UMINUS : UNDEFUIEND;
  } else {
    return (type == PLUS) ? PLUS : (type == MINUS) ? MINUS : UNDEFUIEND;
  }
}

int SmartCalc::ExtractTokenFromStr(std::string &str, bool *flag) {
  int result = UNDEFUIEND;
  if (str[it_] == ' ') {
    ++it_;
    return result;
  }
  if (isdigit(str[it_])) {
    result = NUMBER;
    *flag = false;
  } else {
    auto it = elements_.find(std::string(1, str.at(it_)));
    if (it != elements_.end()) {
      result = it->second;
      if (result == DIV || result == MULT || result == MOD) {
        *flag = true;
      } else if (result == CONST_E || result == CONST_PI || result == X) {
        *flag = false;
      } else if (result == PLUS || result == MINUS) {
        result = IsUnarOrBinar(result, *flag);
      } else if (result == COS || result == SIN || result == ACOS ||
                 result == LN || result == TAN) {
        result = GetTypeFunction(str, result);
        if (result == UNDEFUIEND)
          Error("invalid expressions !");
      }
    }
  }
  ShiftIterator(result);
  return result;
}

SmartCalc::data SmartCalc::ExtractNumFromStr(std::string &str, data value,
                                             size_t *length) {
  data result = 0;
  if (value == NUMBER) {
    result = std::stod(str, length);
  } else {
    result = value == CONST_PI ? M_PI : value == CONST_E ? M_E : 0;
    *length += 1;
  }
  return result;
}

int SmartCalc::GetPriority(int operation) {
  int result = 0;
  if (operation == PLUS || operation == MINUS) {
    result = LOW;
  } else if (operation == DIV || operation == MULT || operation == MOD) {
    result = MID;
  } else if (operation == POW) {
    result = HIGH;
  } else if (operation == UMINUS || operation == UPLUS) {
    result = VHIGH;
  } else if ((operation >= COS && operation <= LN) &&
             (operation != UPLUS && operation != UMINUS)) {
    result = VVHIGH;
  }
  return result;
}

void SmartCalc::PolishNotation(std::string &str) {
  ValidateBrackets(str);
  bool flag = true;
  if (str.length() == 0)
    Error("empty str !");
  do {
    int token = ExtractTokenFromStr(str, &flag);
    if (token == DOT)
      Error("invalid expressions !");
    if (token >= CONST_E && token <= NUMBER) {
      std::string num = str.substr(it_);
      size_t len = 0;
      numbers_.push_back(
          std::make_pair(ExtractNumFromStr(num, token, &len), NUMBER));
      it_ += len;
    } else if (token == OPEN_BRACKET) {
      operations_.push_back(std::make_pair(token, OPERATION));
    } else if (token == CLOSE_BRACKET) {
      AddBrackets();
    } else if (token >= PLUS && token <= LN) {
      AddOperation(token);
    } else if (token == X) {
      numbers_.push_back(std::make_pair(token, X));
    }
  } while (str[it_]);
  TransferData();
  Validate(numbers_);
}

void SmartCalc::AddOperation(int token) {
  if (operations_.empty() ||
      GetPriority((operations_.back().first)) < GetPriority(token)) {
    operations_.push_back(std::make_pair(token, OPERATION));
  } else {
    while (!operations_.empty()) {
      if (GetPriority(operations_.back().first) >= GetPriority(token)) {
        numbers_.push_back(std::make_pair(operations_.back().first, OPERATION));
        operations_.pop_back();
      } else {
        break;
      }
    }
    operations_.push_back(std::make_pair(token, OPERATION));
  }
}

void SmartCalc::AddBrackets() {
  int tmp = operations_.back().first;
  operations_.pop_back();
  while (!operations_.empty() && tmp != OPEN_BRACKET) {
    numbers_.push_back(std::make_pair(tmp, OPERATION));
    tmp = operations_.back().first;
    operations_.pop_back();
  }
}

void SmartCalc::TransferData() {
  while (!operations_.empty()) {
    numbers_.push_back(std::make_pair(operations_.back().first, OPERATION));
    operations_.pop_back();
  }
}

}; // namespace model
