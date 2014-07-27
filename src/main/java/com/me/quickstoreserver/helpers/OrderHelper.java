package com.me.quickstoreserver.helpers;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.me.quickstoreserver.DAOS.InventoryDAO;
import com.me.quickstoreserver.DAOS.ProductDAO;

@Service
public class OrderHelper {

@Autowired
private ProductDAO productDAO;
@Autowired
private InventoryDAO inventoryDAO;

public void settleInventories(HashMap<String, String> prodMap)
{	
int prodId;
int qty;
Iterator<Entry<String, String>> it = prodMap.entrySet().iterator();
while(it.hasNext())
{
Entry<String, String> pairs= it.next();
prodId= Integer.parseInt(pairs.getKey());
qty= Integer.parseInt(pairs.getValue());
inventoryDAO.updateInventories(productDAO.retrieveProductById(prodId), qty);	
}	
}

}