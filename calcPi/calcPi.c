/**
 * File: calcPi.c
 *
 * A program that calculates pi based on the infinite series pi =
 * 4(1 - 1/3 + 1/5 - 1/7...)
 *
 * @author Mark Philip (msp3430)
 */

#include <stdio.h>
#include <unistd.h>

static int NUM_CYCLES  = 1000000;

/**
 * Function: calcPi()
 *
 * Description: Returns the value of pi based on the old value and the 
 *              provided denominator.
 *
 * @param val   the old value of pi
 * @param d     the denominator to use while calculating the new value
 *
 * @return      the new value of pi
 */
static long double calcPi(long double val, long double d) {
  return val + 1.0/d;
}

int main() {

  char tmp = NULL;

  //printout + prompts
  printf("Pi Calculator, by Mark Philip\n");
  printf("NOTE: Using this method, it is only possible to get 62 digits of precision due to the limitations of the data types used.\n");
  printf("Press ctrl+c to quit at any time.\n");
  printf("Show denominator used for each calculation [Y/n]? ");
  scanf("%c", &tmp);

  //set the initial values
  double mult = 1.0;
  long double denom = 1.0;
  long double val = 0.0;
  long long int x = 0;

  //calculate pi (printing denom)
  if (tmp == 'y' || tmp == 'Y') {
    while((val = calcPi(val, mult * denom))) {
      if (x%NUM_CYCLES == 0) {
	printf("Denominator = %.Lf\n", denom);
	printf("pi = %.62Lf\n", 4.0*val);
      }
      x++;
      denom+= 2.0;
      mult = -1.0 * mult;
    }
  }
  //calculate pi (w/o printing denom)
  else if (tmp == 'n' || tmp == 'N') {
    while((val = calcPi(val, mult * denom))) {
      if (x%NUM_CYCLES == 0) {
	printf("pi = %.62Lf\n", 4.0*val);
      }
      x++;
      denom+= 2.0;
      mult = -1.0 * mult;
    }
  }
  else {
    printf("Not a valid input. Re run the program and choose from 'y' or 'n'\n");
    return 1;
  }
}
