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

    double range = xMax - xMin;

    const int maxPoints = 1000;
    int numPoints = std::min(numPoints, maxPoints);

    double step = range / numPoints;
    if (range > 100) {
        step *= (range / 100);
    }
    for (double X = xMin; X <= xMax; X += step) {
        result.first.push_back(X);
        result.second.push_back(RPN(str, X));
        ++i;
    }
    return result;
}

} // namespace model