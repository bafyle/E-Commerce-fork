package com.vodafone.ecommerce.exception;

import com.vodafone.ecommerce.model.ErrorDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class MVCExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MVCException.class)
    public ModelAndView handleApiException(MVCException mvcException, WebRequest request){
//        ErrorDetails errorDetails = new ErrorDetails();
//        errorDetails.setCode(mvcException.getStatus().getReasonPhrase());
//        errorDetails.setMessage(mvcException.getMessage());

        ModelAndView modelAndView =  new ModelAndView(mvcException.getView());
        modelAndView.addObject("errorTitle", "An error has occured");
        modelAndView.addObject("errorStatus", mvcException.getStatus().getReasonPhrase());
        modelAndView.addObject("errorMessage", mvcException.getMessage());
        modelAndView.setStatus(mvcException.getStatus());

        return modelAndView;
    }

//    @Override TODO: Why Does It Exist?
//    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails();
//        errorDetails.setCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
//        errorDetails.setMessage(ex.getMessage());
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//    }
}
