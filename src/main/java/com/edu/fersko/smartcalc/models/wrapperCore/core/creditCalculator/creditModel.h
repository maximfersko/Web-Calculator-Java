#ifndef SRC_MODEL_CREDITMODEL_H_
#define SRC_MODEL_CREDITMODEL_H_

#include <cmath>
#include <iostream>
namespace model {

struct creditData {
  double totalPayment{};
  double monthlyPayment{};
  double overPayment{};
  double minMonthlyPayment{};
  double maxMonthlyPayment{};
  double payments{};
};

class creditModel {
public:
  using data_c_t_ = creditData;

private:
  data_c_t_ data_;

public:
  creditModel() = default;
  ~creditModel() = default;

  void annuity(double summa, double period, double rate);
  void deffirentated(double summa, double rate, double period);
  void validate(double period, double rate, double creditSum);
  double round(double number);
  void clear();
  data_c_t_ getResult() { return data_; }
};

}; // namespace model
#endif