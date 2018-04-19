package itc.automatas2.lexico.automatas;


public class AutomataReservadas {

    public boolean ejecutar(String token) {
        return q0(0, token.toCharArray());
    }

    private boolean q0(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            switch (Character.toString(car[cont])) {
                case "w":
                    return this.q1(cont + 1, car);
                case "e":
                    return this.q2(cont + 1, car);
                case "i":
                    return this.q3(cont + 1, car);
                case "f":
                    return this.q4(cont + 1, car);
                case "r":
                    return this.q5(cont + 1, car);
                case "t":
                    return this.q6(cont + 1, car);
                case "n":
                    return this.q7(cont + 1, car);
                case "b":
                    return this.q8(cont + 1, car);
                case "s":
                    return this.q9(cont + 1, car);
                case "p":
                    return this.q37(cont + 1, car);

            }
        }
        return false;
    }

    private boolean q1(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("h")) {
                return this.q10(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q10(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("i")) {
                return this.q11(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q11(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("l")) {
                return this.q12(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q12(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("e")) {
                return this.qf(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q2(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("l")) {
                return this.q13(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q13(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("s")) {
                return this.q14(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q14(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("e")) {
                return this.qf(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q3(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            switch (Character.toString(car[cont])) {
                case "n":
                    return this.q15(cont + 1, car);
                case "f":
                    return this.qf(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q15(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("t")) {
                return this.qf(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q4(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            switch (Character.toString(car[cont])) {
                case "o":
                    return this.q16(cont + 1, car);
                case "u":
                    return this.q17(cont + 1, car);
                case "a":
                    return this.q19(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q16(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("r")) {
                return this.qf(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q17(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("n")) {
                return this.q18(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q18(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("c")) {
                return this.qf(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q19(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("l")) {
                return this.q20(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q20(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("s")) {
                return this.q21(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q21(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("e")) {
                return this.qf(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q5(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("e")) {
                return this.q22(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q22(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            switch (Character.toString(car[cont])) {
                case "t":
                    return this.q23(cont + 1, car);
                case "a":
                    return this.q26(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q23(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("u")) {
                return this.q24(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q24(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("r")) {
                return this.q25(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q25(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("n")) {
                return this.qf(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q26(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            switch (Character.toString(car[cont])) {
                case "l":
                case "d":
                    return this.qf(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q6(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("r")) {
                return this.q27(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q27(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("u")) {
                return this.q28(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q28(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("e")) {
                return this.qf(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q7(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("u")) {
                return this.q29(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q29(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("l")) {
                return this.q30(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q30(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("l")) {
                return this.qf(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q8(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("o")) {
                return this.q31(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q31(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("o")) {
                return this.q32(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q32(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("l")) {
                return this.qf(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q9(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("t")) {
                return this.q33(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q33(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("r")) {
                return this.q34(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q34(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("i")) {
                return this.q35(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q35(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("n")) {
                return this.q36(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q36(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("g")) {
                return this.qf(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q37(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("r")) {
                return this.q38(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q38(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("i")) {
                return this.q39(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q39(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("n")) {
                return this.q40(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q40(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("t")) {
                return this.q41(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q41(int cont, char[] car) {
        if (cont == car.length) {
            return true;
        } else {
            if (Character.toString(car[cont]).equals("l")) {
                return this.q42(cont + 1, car);
            }
        }
        return false;
    }

    private boolean q42(int cont, char[] car) {
        if (cont == car.length) {
            return false;
        } else {
            if (Character.toString(car[cont]).equals("n")) {
                return this.qf(cont + 1, car);
            }
        }
        return false;
    }

    private boolean qf(int cont, char[] car) {
        if (cont == car.length) {
            return true;
        } else {
            return false;
        }
    }
}
