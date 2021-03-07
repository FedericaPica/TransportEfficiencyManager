package com.java.tem.exceptions;

public class DoesNotBelongToAzienda extends Throwable {
  public DoesNotBelongToAzienda(String s) {
    super(s);
  }
}
