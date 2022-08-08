package by.teachmeskills.eshop.utils;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.dto.converters.ProductConverter;
import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.entities.User;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class CsvUtil<T> {
    private final ProductConverter productConverter;

    public CsvUtil(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    public List<Category> parseCsvCategory(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<Category> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Category.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(',')
                        .build();
                return csvToBean.parse();
            } catch (Exception ex) {
                log.error("Exception occurred during CSV parsing: {}", ex.getMessage());
            }
        } else {
            log.error("Empty CSV file is uploaded.");
        }
        return Collections.emptyList();
    }

    public void saveCategoriesToCsvFile(Writer writer, List<Category> categories) {
        try {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(categories);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public List<Product> parseCsvProduct(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<ProductDto> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(ProductDto.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(',')
                        .build();
                List<ProductDto> productDtos = csvToBean.parse();
                return Optional.ofNullable(productDtos)
                        .map(list -> list.stream()
                                .map(productConverter::fromDto)
                                .toList())
                        .orElse(null);
            } catch (Exception ex) {
                log.error("Exception occurred during CSV parsing: {}", ex.getMessage());
            }
        } else {
            log.error("Empty CSV file is uploaded.");
        }
        return Collections.emptyList();
    }

    public void saveProductsToCsvFile(Writer writer, List<Product> products) {
        try {
            List<ProductDto> productDtos = Optional.ofNullable(products)
                    .map(list -> list.stream()
                            .map(productConverter::toDto)
                            .toList())
                    .orElse(null);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(productDtos);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void saveProductsToCsvFile(Writer writer, Order order) {
        try {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(order);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void saveAllUsersToCsvFile(Writer writer, List<User> users) {
        try {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(users);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}