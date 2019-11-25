package com.oepl.core;

/**
 * Project sib-service
 *
 * @author nishantb
 * @version 1.0
 * @date 02-Feb-2018
 */
public class Response {

  private int status;

  private String message;

  private Object data;

  /**
   *
   */
  public Response() {

    super();
  }

  /**
   * @param status
   * @param message
   */
  public Response(final int status, final String message) {

    super();
    this.status = status;
    this.message = message;
  }

  /**
   * @param status
   * @param message
   * @param data
   */
  public Response(final int status, final String message, final Object data) {

    this.status = status;
    this.message = message;
    this.data = data;
  }

  /**
   * @return
   */
  public int getStatus() {

    return this.status;
  }

  /**
   * @param status
   */
  public void setStatus(final int status) {

    this.status = status;
  }

  /**
   * @return
   */
  public String getMessage() {

    return this.message;
  }

  /**
   * @param message
   */
  public void setMessage(final String message) {

    this.message = message;
  }

  /**
   * @return the data
   */
  public Object getData() {

    return this.data;
  }

  /**
   * @param data the data to set
   */
  public void setData(final Object data) {

    this.data = data;
  }
}
