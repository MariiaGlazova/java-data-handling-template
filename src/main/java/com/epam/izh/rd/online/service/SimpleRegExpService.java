package com.epam.izh.rd.online.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        String string = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("src\\main\\resources\\sensitive_data.txt")))) {
            string = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string.replaceAll("(\\s\\d{4})(\\s\\d{4}\\s\\d{4}\\s)(\\d{4}\\s)", "$1 **** **** $3");
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String string = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/sensitive_data.txt"))) {
            string = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string.replace("${payment_amount}", String.valueOf((int) paymentAmount)).replace("${balance}", String.valueOf((int) balance));
    }
}
