package livraria.livrariafx.model.exceptions;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<String, String> errors = new HashMap<>();

    public ValidationException(String msg){
        super(msg);
    }

    public Map<String, String> getErrors(){
        return errors;
    }

    public void addError(String fieldName, String errorMessage){
        errors.put(fieldName, errorMessage);
    }
}
