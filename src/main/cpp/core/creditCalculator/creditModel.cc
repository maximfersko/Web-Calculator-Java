#include "creditModel.h"

namespace model {

double creditModel::round(double number) {
  return (std::round(number * 100)) / 100;
}

void creditModel::annuity(double summa, double period, double rate) {
  clear();
  validate(period, rate, summa);
  double montRate = rate / (12 * 100);
  double bottom = (1 - (1 / std::pow(montRate + 1, period)));
  data_.monthlyPayment = round((summa * montRate) / bottom);
  data_.totalPayment = round(data_.monthlyPayment * period);
  data_.overPayment = round(data_.totalPayment - summa);
}

void creditModel::clear() {
  data_.totalPayment = 0;
  data_.minMonthlyPayment = 0;
  data_.monthlyPayment = 0;
  data_.overPayment = 0;
  data_.maxMonthlyPayment = 0;
}

void creditModel::deffirentated(double summa, double rate, double period) {
  clear();
  validate(period, rate, summa);
  double monthRate = rate / (12 * 100);
  double remainSumma = summa;
  double mainDebt = summa / period;
  for (int i = 0; i < period; ++i) {
    double persents = remainSumma * monthRate;
    remainSumma -= mainDebt;
    data_.minMonthlyPayment = persents + mainDebt;
    data_.totalPayment += data_.minMonthlyPayment;
    if (i == 0)
      data_.maxMonthlyPayment = data_.minMonthlyPayment;
  }
  data_.totalPayment = round(data_.totalPayment);
  data_.overPayment = round(data_.totalPayment - summa);
}

void creditModel::validate(double period, double rate, double creditSum) {
  if (period >= 660 || period <= 0)
    throw std::invalid_argument(
        "Incorrect parametr! 0 > Loan amount < 55 (years)");
  if (creditSum <= 0)
    throw std::invalid_argument("Incorrect parametr! Loan Term > 0");
}

}; // namespace model
