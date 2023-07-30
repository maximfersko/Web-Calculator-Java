#include "smartCalc.h"

namespace model {

std::pair<std::vector<double>, std::vector<double>>
SmartCalc::graphBuilder(std::vector<double> &Data, std::string str) {
  std::pair<std::vector<double>, std::vector<double>> result;
  double xMax = Data.back();
  Data.pop_back();
  double xMin = Data.back();
  Data.pop_back();
  int i = Data.back();
  Data.pop_back();
  double step = (xMax - xMin) / 1e4;
  for (double X = xMin; X <= xMax; X += step) {
    result.first.push_back(X);
    result.second.push_back(RPN(str, X));
    ++i;
  }
  return result;
}

} // namespace model