package Nhom7.car_ecommerce.response;

import java.util.ArrayList;
import java.util.List;

import Nhom7.car_ecommerce.modal.User;
import lombok.Data;
@Data
public class WatchListResponse {
    private long favouriteId;
    private User user;
    private List<CarWatchlishResponse> cars = new ArrayList<>();
}
