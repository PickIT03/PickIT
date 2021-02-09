package com.example.pickit.shopowner.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.pickit.shopowner.model.OrderModel;
import com.example.pickit.shopowner.model.Product;
import com.example.pickit.shopowner.repo.OrderRepo;
import com.example.pickit.shopowner.repo.ProductRepo;

import java.util.List;

public class ShopOwnerViewModel extends ViewModel
{
    ProductRepo productRepo = new ProductRepo();
    public LiveData<List<Product>> getProducts(){
        return productRepo.getProducts();
    }
    OrderRepo orderRepo = new OrderRepo();
    public LiveData<List<OrderModel>> getOrder(){
        return  orderRepo.getProducts();
    }
}
