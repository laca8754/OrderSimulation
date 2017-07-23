package com.ttk.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.ttk.model.Order;
import com.ttk.model.OrderItem;
import com.ttk.model.ResponseStatus;
import com.ttk.model.Status;
import com.ttk.pojo.ResponseData;
import com.ttk.service.impl.OrderItemServiceImpl;
import com.ttk.service.impl.OrderServiceImpl;
import com.ttl.dao.impl.OrderDaoImpl;
import com.ttl.dao.impl.OrderItemDaoImpl;

public class CsvFileReader {

	private static final String DELIMITER = ";";
	private static final String INPUT_FILE = "inputfile/inputFile.csv";
	private static final int LINE_NUMBER_IDX = 0;
	private static final int ORDER_ITEM_ID_IDX = 1;
	private static final int ORDER_ID_IDX = 2;
	private static final int BUYER_NAME_IDX = 3;
	private static final int BUYER_EMAIL_IDX = 4;
	private static final int ADDRESS_IDX = 5;
	private static final int POSTCODE_IDX = 6;
	private static final int SALE_PRICE_IDX = 7;
	private static final int SHIPPING_PRICE_IDX = 8;
	private static final int SKU_IDX = 9;
	private static final int STATUS_IDX = 10;
	private static final int ORDER_DATE_IDX = 11;
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static List<ResponseData> responseDataList;
	private List<Order> orders;
	private List<OrderItem> orderItems;
	private static int lineNumber;

	public void readCsvFile() {

		BufferedReader fileReader = null;

		try {

			String line = "";
			responseDataList = new ArrayList<>();
			orders = new ArrayList<>();
			orderItems = new ArrayList<>();

			// Create the file reader
			fileReader = new BufferedReader(new FileReader(INPUT_FILE));

			// checking the file is not empty
			if (Files.size(Paths.get(INPUT_FILE)) > 0) {

				// reading lines
				while ((line = fileReader.readLine()) != null) {
					int orderItemId, orderId, postCode;
					double salePrice, shippingPrice;
					String buyerName, buyerEmail, address, sku, status;
					Date orderDate;
					// Get all tokens available in line
					String[] tokens = line.split(DELIMITER);
					if (tokens.length > 0) {

						// checking lineNumber
						if (checkLineNumber(tokens[LINE_NUMBER_IDX])) {
							lineNumber = Integer.parseInt(tokens[LINE_NUMBER_IDX]);
						} else {
							continue;
						}

						// checking orderItemId
						if (checkOrderItemId(tokens[ORDER_ITEM_ID_IDX])) {
							orderItemId = Integer.parseInt(tokens[ORDER_ITEM_ID_IDX]);
						} else {
							continue;
						}

						// checking orderId

						if (checkOrderId(tokens[ORDER_ID_IDX])) {
							orderId = Integer.parseInt(tokens[ORDER_ID_IDX]);
						} else {
							// orderId = Integer.parseInt(tokens[ORDER_ID_IDX]);
							continue;
						}

						// checking buyerName

						if (checkBuyerName(tokens[BUYER_NAME_IDX])) {
							buyerName = tokens[BUYER_NAME_IDX];
						} else {
							continue;
						}

						// checking buyerEmail

						if (checkBuyerEmail(tokens[BUYER_EMAIL_IDX])) {
							buyerEmail = tokens[BUYER_EMAIL_IDX];
						} else {
							continue;
						}

						// checking address

						if (checkAddress(tokens[ADDRESS_IDX])) {
							address = tokens[ADDRESS_IDX];
						} else {
							continue;
						}

						// checking postcode

						if (checkPostCode(tokens[POSTCODE_IDX])) {
							postCode = Integer.parseInt(tokens[POSTCODE_IDX]);
						} else {
							continue;
						}

						// checking salePrice

						if (checkSalePrice(tokens[SALE_PRICE_IDX])) {
							salePrice = Double.parseDouble(tokens[SALE_PRICE_IDX]);
						} else {
							continue;
						}

						// checking shippingPrice

						if (checkShippingPrice(tokens[SHIPPING_PRICE_IDX])) {
							shippingPrice = Double.parseDouble(tokens[SHIPPING_PRICE_IDX]);
						} else {
							continue;
						}

						// checking sku

						if (checkSku(tokens[SKU_IDX])) {
							sku = tokens[SKU_IDX];
						} else {
							continue;
						}

						// checking status

						if (checkStatus(tokens[STATUS_IDX])) {
							status = tokens[STATUS_IDX];
						} else {
							continue;
						}

						// checking orderDate

						try {
							Date date = checkOrderDate(tokens[ORDER_DATE_IDX]);

							if (date != null) {
								orderDate = date;
							} else {
								continue;
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							orderDate = new Date();
						}

						double totalPrice = salePrice + shippingPrice;

						// add elements to lists

						Order newOrder = new Order(orderId, buyerName, buyerEmail, orderDate,
								BigDecimal.valueOf(totalPrice), address, postCode);
						OrderItem newOrderItem = new OrderItem(orderItemId, newOrder, BigDecimal.valueOf(salePrice),
								BigDecimal.valueOf(shippingPrice), BigDecimal.valueOf(totalPrice), sku,
								Status.valueOf(status));

						boolean exist = false;
						boolean error = false;

						// add correct elements to order list

						if (!orders.isEmpty()) {
							for (Order order : orders) {
								if (order.getOrderId() == newOrder.getOrderId()) {
									// order.setOrderTotalValue(
									// newOrder.getOrderTotalValue().add(order.getOrderTotalValue()));
									exist = true;
									break;
								}

							}
						} else {
							orders.add(newOrder);
							exist = true;
						}

						if (!exist)
							orders.add(newOrder);

						// add correct elements to orderItem list

						exist = false;
						if (!orderItems.isEmpty()) {
							for (OrderItem item : orderItems) {
								if (item.getOrderItemId() == newOrderItem.getOrderItemId()) {
									responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR,
											"orderItemId is already exist in the file"));
									exist = true;
									error = true;
									break;
								}
							}
						} else {
							orderItems.add(newOrderItem);
							exist = true;
						}

						if (!exist) {
							orderItems.add(newOrderItem);
							for (Order order : orders) {
								if (order.getOrderId() == newOrder.getOrderId())
									order.setOrderTotalValue(
											newOrder.getOrderTotalValue().add(order.getOrderTotalValue()));
							}

						}

						if (!error)
							responseDataList.add(new ResponseData(lineNumber, ResponseStatus.OK, ""));

					}
				}
			} else {
				responseDataList.add(new ResponseData(0, ResponseStatus.ERROR, "inputFile is empty"));
			}

		} catch (Exception e) {
			System.out.println("Error in CsvFileReader !");
			responseDataList.add(new ResponseData(0, ResponseStatus.ERROR, e.toString()));
			e.printStackTrace();
		} finally {
			try {
				fileReader.close(); // closing the file

				for (ResponseData response : responseDataList) {
					System.out.println(response.toString());
				}

			} catch (IOException e) {
				System.out.println("Error while closing fileReader !");
				responseDataList.add(new ResponseData(0, ResponseStatus.ERROR, "Error while closing fileReader!"));
				e.printStackTrace();
			}
		}

	}

	public void addItemsIntoDb() {
		try {
			OrderServiceImpl orderService = new OrderServiceImpl();
			if (!orders.isEmpty())
				orderService.addOrders(orders);
			OrderItemServiceImpl orderItemService = new OrderItemServiceImpl();
			if (!orderItems.isEmpty())
				orderItemService.addOrderItems(orderItems);
		} catch (Exception e) {
			System.out.println("Error while writing to data into the database !");
			responseDataList
					.add(new ResponseData(0, ResponseStatus.ERROR, "Error while writing to data into the database !"));
			e.printStackTrace();
		}
	}

	/**
	 * return true if lineNumber is correct else return false
	 *
	 */
	private static boolean checkLineNumber(String lineNumber) {
		boolean valid = false;
		if (checkDataEmpty(lineNumber)) {
			if (checkValidNumber(lineNumber)) {
				if (checkPositiveValue(lineNumber)) {
					valid = true;

				} else {
					responseDataList.add(new ResponseData(0, ResponseStatus.ERROR, "linenumber has to be more than 0"));
				}

			} else {
				responseDataList.add(new ResponseData(0, ResponseStatus.ERROR, "linenumber is not a number"));
			}

		} else {
			responseDataList.add(new ResponseData(0, ResponseStatus.ERROR, "linenumber is empty"));
		}
		return valid;
	}

	/**
	 * return true if orderItemId is correct else return false
	 *
	 */
	private static boolean checkOrderItemId(String orderItemId) {
		boolean valid = false;
		if (checkDataEmpty(orderItemId)) {
			if (checkValidNumber(orderItemId)) {
				try {
					OrderItemDaoImpl orderItemDao = new OrderItemDaoImpl();
					if (orderItemDao.getItemById(Integer.parseInt(orderItemId)) == null) {
						valid = true;
					} else {
						responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR,
								"orderItemId is already exist in the database"));
					}
				} catch (NumberFormatException e) {
					responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, e.toString()));
					e.printStackTrace();
				} catch (Exception e) {
					responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, e.toString()));
					e.printStackTrace();
				}

			} else {
				responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "orderItemId is not a number"));
			}

		} else {
			responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "orderItemId is empty"));
		}
		return valid;
	}

	/**
	 * return true if orderId is correct else return false
	 *
	 */
	private static boolean checkOrderId(String orderId) {
		boolean valid = false;
		if (checkDataEmpty(orderId)) {
			if (checkValidNumber(orderId)) {
				try {
					OrderDaoImpl orderDao = new OrderDaoImpl();
					if (orderDao.getItemById(Integer.parseInt(orderId)) == null) {
						valid = true;
					} else {
						responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR,
								"orderId has already exist in the database"));
					}

				} catch (NumberFormatException e) {
					responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, e.toString()));
					e.printStackTrace();
				} catch (Exception e) {
					responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, e.toString()));
					e.printStackTrace();
				}

			} else {
				responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "orderId is not a number"));
			}

		} else {
			responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "orderId is empty"));
		}
		return valid;
	}

	/**
	 * return true if name is correct else return false
	 *
	 */
	private static boolean checkBuyerName(String buyerName) {
		boolean valid = false;
		if (checkDataEmpty(buyerName)) {
			valid = true;
		} else {
			responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "buyerName is empty"));
		}
		return valid;
	}

	/**
	 * return true if email is correct else return false
	 *
	 */
	private static boolean checkBuyerEmail(String buyerEmail) {
		boolean valid = false;
		if (checkDataEmpty(buyerEmail)) {

			if (EmailValidator.getInstance().isValid(buyerEmail)) {
				valid = true;
			} else {
				responseDataList.add(
						new ResponseData(lineNumber, ResponseStatus.ERROR, "buyerEmail is not valid email addresss"));
			}

		}

		else {
			responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "buyerEmail is empty"));
		}

		return valid;
	}

	/**
	 * return true if address is correct else return false
	 *
	 */
	private static boolean checkAddress(String address) {
		boolean valid = false;

		if (checkDataEmpty(address)) {
			valid = true;
		}

		else {
			responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "address is empty"));
		}

		return valid;
	}

	/**
	 * return true if postCode is correct else return false
	 *
	 */
	private static boolean checkPostCode(String postCode) {
		boolean valid = false;

		if (checkDataEmpty(postCode)) {
			if (checkValidNumber(postCode)) {
				if (checkPositiveValue(postCode)) {
					valid = true;
				} else {
					responseDataList
							.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "postcode has to be more than 0"));
				}

			} else {
				responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "postcode is not a number"));
			}

		} else {
			responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "postcode is empty"));
		}

		return valid;
	}

	/**
	 * return true if salePrice is correct else return false
	 *
	 */
	private static boolean checkSalePrice(String salePrice) {
		boolean valid = false;

		if (checkDataEmpty(salePrice)) {
			if (checkValidNumber(salePrice)) {

				if (Double.parseDouble(salePrice) >= 1) {
					valid = true;
				} else {
					responseDataList
							.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "salePrice has to be more than 1"));
				}

			} else {
				responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "salePrice is not a number"));
			}

		} else {
			responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "salePrice is empty"));
		}

		return valid;
	}

	/**
	 * return true if ShippngPrice is correct else return false
	 *
	 */
	private static boolean checkShippingPrice(String shippingPrice) {
		boolean valid = false;

		if (checkDataEmpty(shippingPrice)) {
			if (checkValidNumber(shippingPrice)) {
				if (checkPositiveValue(shippingPrice)) {
					valid = true;
				} else {
					responseDataList.add(
							new ResponseData(lineNumber, ResponseStatus.ERROR, "shippingPrice has to be more than 0"));
				}
			} else {
				responseDataList
						.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "shippingPrice is not a number"));
			}

		} else {
			responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "shippingPrice is empty"));
		}

		return valid;
	}

	/**
	 * return true if SKU is correct else return false
	 *
	 */
	private static boolean checkSku(String sku) {
		boolean valid = false;

		if (checkDataEmpty(sku)) {
			valid = true;
		}

		else {
			responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "sku is empty"));
		}

		return valid;
	}

	/**
	 * return true if status is correct else return false
	 *
	 */
	private static boolean checkStatus(String status) {
		boolean valid = false;

		if (checkDataEmpty(status)) {
			if (status.equals(Status.IN_STOCK.toString()) || status.equals(Status.OUT_OF_STOCK.toString())) {
				valid = true;

			} else {
				responseDataList
						.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "value of status is incorrect"));
			}
		} else {
			responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "status is empty"));
		}

		return valid;
	}

	/**
	 * return true if date is correct else return false
	 *
	 */
	private static Date checkOrderDate(String orderDate) {
		Date date = null;

		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		df.setLenient(false);
		try {
			date = df.parse(orderDate);
		} catch (ParseException e) {
			responseDataList.add(new ResponseData(lineNumber, ResponseStatus.ERROR, "date is invalid"));
		}

		return date;
	}

	/**
	 * return true if value is not empty else return false
	 *
	 */
	private static boolean checkDataEmpty(String adat) {

		boolean valid = StringUtils.isNotBlank(adat);
		return valid;

	}

	/**
	 * return true if value is number else return false
	 *
	 */
	private static boolean checkValidNumber(String adat) {
		try {
			double number = Double.parseDouble(adat);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}

	/**
	 * return true if value is positve else return false
	 *
	 */
	private static boolean checkPositiveValue(String number) {
		boolean valid = false;
		if (Double.parseDouble(number) >= 0) {
			valid = true;
		}

		return valid;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public List<ResponseData> getResponseDataList() {
		return responseDataList;
	}

}
