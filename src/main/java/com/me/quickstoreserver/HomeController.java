package com.me.quickstoreserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.me.quickstoreserver.DAOS.CustomerDAO;
import com.me.quickstoreserver.DAOS.InventoryDAO;
import com.me.quickstoreserver.DAOS.OrderDAO;
import com.me.quickstoreserver.DAOS.Order_ProductDAO;
import com.me.quickstoreserver.DAOS.ProductDAO;
import com.me.quickstoreserver.DAOS.StoreDAO;
import com.me.quickstoreserver.Pojos.Customer;
import com.me.quickstoreserver.Pojos.Inventory;
import com.me.quickstoreserver.Pojos.Order;
import com.me.quickstoreserver.Pojos.Order_Product;
import com.me.quickstoreserver.Pojos.Product;
import com.me.quickstoreserver.Pojos.Store;
import com.me.quickstoreserver.helpers.MiscHelper;
import com.me.quickstoreserver.helpers.OrderHelper;

/**
 * Handles requests for the application home page.
 */
@SuppressWarnings({ "unchecked" })
@Controller
public class HomeController {

	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private StoreDAO storeDAO;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private InventoryDAO inventoryDAO;
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private Order_ProductDAO opDAO;
	@Autowired
	private OrderHelper orderHelper;
	@Autowired
	private MiscHelper miscHelper;

	private final static Logger logger = Logger.getLogger(HomeController.class
			.getName());

	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.debug("Welcome home! The client locale is " + locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	// USER AUTHENTICATION VERIFICATION rest
	@RequestMapping(value = "/isCustomerAuthenticated", method = RequestMethod.GET)
	public @ResponseBody
	String isCustomerAuthenticated(@RequestParam("x") String x,
			@RequestParam("y") String y) {
		logger.info("rest hit for isCustomerAuthenticated");
		org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
		if (customerDAO.isUserAuthenticated(x, y)) {
			obj.put("auth", "yes");
		} else {
			obj.put("auth", "no");
		}

		return obj.toJSONString();
	}

	// WEATHER A USER EXISTS ALREADY

	@RequestMapping(value = "/doesSuchUserExist", method = RequestMethod.GET)
	public @ResponseBody
	String doesSuchUSerExist(@RequestParam("x") String x) {
		logger.info("rest hit for doesSuchUserExist");
		JSONObject obj = new JSONObject();
		Customer c = customerDAO.retrieveCustomerByUserName(x);
		if (c != null) {
			obj.put("msg", "exists");
		} else {
			obj.put("msg", "doesn't exist");
		}
		return obj.toJSONString();
	}

	// RETURN PRODUCT DETAILS ON SCAN
	@RequestMapping(value = "/getMeTheProductDetails", method = RequestMethod.GET)
	public @ResponseBody
	String retrieveTheProductDetails(@RequestParam("x") String x) {
		org.json.JSONObject obj = new org.json.JSONObject();
		int prodId;
		try {
			prodId = Integer.parseInt(x);
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
			obj.put("info", "Enter a number");
			return obj.toString();
		}
		Product prod = productDAO.retrieveProductById(prodId);
		if (prod != null) {
			obj = new org.json.JSONObject(prod);
		} else {
			obj.put("info", "No Such Value Found");
		}
		return obj.toString();

	}

	// RETURN RESPECTIVE WALLMART STORE BY ZIPCODE
	@RequestMapping(value = "/getMeTheStore", method = RequestMethod.GET)
	public @ResponseBody
	String retriveStoreInfo(@RequestParam("x") String x) {
		org.json.JSONObject obj = new org.json.JSONObject();
		int streId;
		try {
			streId = Integer.parseInt(x);
		} catch (NumberFormatException nfe) {
			obj.put("info", "Enter a number");
			return obj.toString();
		}
		Store s = storeDAO.retrieveStoreByZip(streId);
		if (s != null) {
			obj = new org.json.JSONObject(s);
		} else {
			obj.put("info", "No Such Value Found");
		}
		return obj.toString();
	}

	// RETURN ALL THE STORES TO BE DISPLAYED IN THE FIRST TAB
	@RequestMapping(value = "/getMeAllStores", method = RequestMethod.GET)
	public @ResponseBody
	String retriveAllStores() {
		logger.info("rest hit for getMeAllStores");
		org.json.JSONArray obj;
		List<Store> s = storeDAO.retrieveStores();
		if (s != null) {
			obj = new org.json.JSONArray(s);
			return obj.toString();
		} else {
			org.json.JSONObject obj1;
			obj1 = new org.json.JSONObject();
			obj1.put("info", "No Such Value Found");
			return obj1.toString();
		}

	}

	@RequestMapping(value = "/getMeTheOrdersByCustomer", method = RequestMethod.GET)
	public @ResponseBody
	String retrieveOrdersByCustomers(@RequestParam("x") String x) {

		Customer c = customerDAO.retrieveCustomerByUserName(x);
		List<Order> oList = orderDAO.retrieveOrdersByCustomer(c);
		if ((oList != null) && (!oList.isEmpty())) {

			JSONArray arr = new JSONArray(oList);
			return arr.toString();
		} else {
			JSONObject obj = new JSONObject();
			obj.put("info", "No Such Value Found");
			return obj.toString();
		}

	}

	@RequestMapping(value = "/getProductCatalog", method = RequestMethod.GET)
	public @ResponseBody
	String retrieveProductCatalogOfStore(@RequestParam("x") String x) {
		JSONObject obj = new JSONObject();
		logger.info("rest hit for getMeAllProducts of the store");
		Store s = storeDAO.retrieveStoreByZip(Integer.parseInt(x));
		List<Inventory> inventories = inventoryDAO
				.prepareProductCatalogForTheStore(s);
		if ((inventories != null) && (!inventories.isEmpty())) {
			JSONArray arr = new JSONArray(inventories);
			return arr.toString();
		}

		else {
			obj.put("info", "No Such Value Found");
			return obj.toString();
		}

	}

	// PLACE ORDER USING THIS METHOD
	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
	public @ResponseBody
	String placeTheOrder(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("rest HIT, FOR PLACE ORDER");
		
		JSONObject obj = new JSONObject();
		JSONObject reqJson;
		
		//Decodes the request String
		String body = miscHelper.requestDeFlater(request);
		if (body == null) {
			logger.error("body deflated with erros, error messages logged above should provide you enough information");
			obj.put("error", "please input proper fromatted json");
			return obj.toString();
		}

		try {
			Object object = JSONValue.parse(body);
			reqJson = (JSONObject) object;
		} catch (JSONException jex) {
			logger.error("caught json exception");
			obj.put("error", "please input proper fromatted json");
			return obj.toString();
		}

		// Local Variables to calculate total & other stuff are being declared
		// here
		// as we need them here

		double orderTotal = 0.0;
		ArrayList<Order_Product> order_prodList = new ArrayList<Order_Product>();
		org.json.simple.JSONObject productMap;

		// end of variable decl section.. processing json now..
		/*
		 * following errors are possible:- 1. user invalid 2. product id invalid
		 * 3. store id mismatch 4. quantity excceding inventory all of them
		 * shall be tackled below with its own error message sent to user:--
		 */

		try {
			productMap = (org.json.simple.JSONObject) reqJson.get("productMap");
		} catch (JSONException jpe) {
			logger.error("json exception caught");
			obj.put("error", "please input proper fromatted json product Map");
			return obj.toString();
		} catch (NullPointerException ne) {
			logger.error("null pointer exception caught..");
			obj.put("error", "please input proper fromatted json product Map");
			return obj.toString();
		}
		Set<String> productIdSet = productMap.keySet();
		ArrayList<String> prodIdList = new ArrayList<String>(productIdSet);
		if (prodIdList.isEmpty()) {
			logger.debug("empty product map");
			obj.put("error",
					"please input proper fromatted json product Map, it can't be empty");
			return obj.toString();

		}
		for (String x : prodIdList) {
			Product pdct;
			try {
				pdct = productDAO.retrieveProductById(Integer.parseInt(x));
			} catch (NumberFormatException ne) {
				obj.put("error", "please input a number for productId");
				return obj.toString();
			} catch (NullPointerException ne) {
				obj.put("error", "please input a number for productId");
				return obj.toString();
			}
			if (pdct == null) {
				obj.put("error", "no such product found");
				return obj.toString();
			}

			int product_Quantity;
			try {
				product_Quantity = Integer.parseInt((String) productMap.get(x));
				if (inventoryDAO.getCurrentInventory(pdct) < product_Quantity) {
					obj.put("error", "insufficient inventory of the product "
							+ pdct.getProductName());
					return obj.toString();
				}
			} catch (NumberFormatException ne) {
				obj.put("error", "couldnt parse one of your inputs");
				return obj.toString();
			}

			try {
				orderTotal = orderTotal + pdct.getProductPrice()
						* product_Quantity;
				logger.info(x);
				Order_Product opdct = new Order_Product();
				opdct.setProduct(pdct);
				opdct.setQuantity(product_Quantity);
				order_prodList.add(opdct);
			} catch (NumberFormatException nee) {
				obj.put("error", "couldnt parse one of your inputs");
				return obj.toString();
			}
		}
		Order order = new Order();
		try {
			order.setCustomer(customerDAO
					.retrieveCustomerByUserName((String) reqJson
							.get("userName")));
			order.setStore(storeDAO.retrieveStoreByZip(Integer
					.parseInt((String) reqJson.get("storeZip"))));
		} catch (NullPointerException ne) {
			obj.put("error", "couldnt retrieve the customer/store");
			return obj.toString();
		}
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = formatter.format(Calendar.getInstance().getTime());

		order.setOrderDate(s);
		order.setTotalAmount(orderTotal);

		// Saving order & then the order-product mappings
		orderDAO.createOrder(order);

		for (Order_Product op : order_prodList) {
			op.setOrder(order);
			opDAO.createOrder_Product(op);
		}

		// updating the inventories
		orderHelper.settleInventories(productMap);

		// Logging the results that were saved
		logger.info("the order total is " + orderTotal);
		logger.info(reqJson.get("userName"));
		logger.info(reqJson.get("storeZip"));

		obj.put("success", "Order Created");
		return obj.toString();
	}

	// SAVE USER VIA SIGN UP
	@RequestMapping(value = "/createCustomer", method = RequestMethod.POST)
	public @ResponseBody
	String createCustomer(HttpServletRequest request,
			HttpServletResponse response) {
		// System.gc();
		logger.debug("rest hit for Create User");
		JSONObject obj = new JSONObject();

		// Variable block
		String body = null;
		ObjectMapper mapper = new ObjectMapper();
		org.json.JSONObject jSObj = null;

		// operations begin
		body= miscHelper.requestDeFlater(request);
		if (body == null) {
			logger.error("body deflated with erros, error messages logged above should provide you enough information");
			obj.put("error", "please input proper fromatted json");
			return obj.toString();
		}
		
		// if we record a string.. we can now parse it and try mapping to save
		// the user..
		try {
			jSObj = new org.json.JSONObject(body.toString());
		} catch (JSONException jex) {
			logger.error("caught json exception");
			obj.put("error", "please input proper fromatted json");
			return obj.toString();
		}

		try {
			Customer cmer = mapper.readValue(jSObj.toString(), Customer.class);
			if (!customerDAO.doesSuchUserExist(cmer.getUserName())) {
				customerDAO.createCustomer(cmer);
				obj.put("success", "User Created");
			} else {
				obj.put("error", "Such User Exists");
			}

		} catch (JsonParseException e) {

			logger.error(e.getMessage());
			obj.put("error", "please input proper fromatted json");
			return obj.toString();
		} catch (JsonMappingException e) {

			logger.error(e.getMessage());
			obj.put("error",
					"please input proper fromatted json, we were unable to process the fields of the json request");
			return obj.toString();
		} catch (IOException e) {

			logger.error(e.getMessage());
			obj.put("error",
					"an error occured at our end, we are investigating, sorry! :(");
			return obj.toString();
		}

		return obj.toString();
	}

	// Update password of the user

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public @ResponseBody
	String updatePassword(HttpServletRequest request,
			HttpServletResponse response) {

		return null;

	}
}