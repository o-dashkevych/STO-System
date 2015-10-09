package run;

import entity.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.controlsfx.dialog.Dialogs;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.loader.custom.NonUniqueDiscoveredSqlAliasException;
import org.hibernate.metamodel.ValidationException;
import run.car.CarAddController;
import run.car.CarController;
import run.car.CarEditController;
import run.client.ClientAddController;
import run.client.ClientController;
import run.client.ClientEditController;
import run.master.MasterAddController;
import run.master.MasterController;
import run.master.MasterEditController;
import run.order1.OrderAddController;
import run.order1.OrderController;
import run.order1.OrderEditController;
import run.order_type.OrderTypeAddController;
import run.order_type.OrderTypeController;
import run.order_type.OrderTypeEditController;
import service.QueryImpl;
import service.car.CarDAOImpl;
import service.client.ClientDAOImpl;
import service.master.MasterDAOImpl;
import service.order1.OrderDAOImpl;
import service.order_type.OrderTypeDAOImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


public class Main extends Application {

    private Stage primaryStage;

    public BorderPane rootLayout;

    private ObservableList<Client> personData;

    private ObservableList<Master> masters;

    private ObservableList<OrderType> orderTypes;

    private ObservableList<Order1> orders;

    private ObservableList<Car> cars;

    private boolean result;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Car Service");
        initRootLayout();
        showMainMenu();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public Main() {
    }
    /*
    *
    * VIEWS INITIALIZATION
    *
    * */

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Root.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
            primaryStage.setMinHeight(500);
            primaryStage.setMinWidth(850);
            RootController controller = loader.getController();
            controller.setMain(this);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/Menu.fxml"));
            AnchorPane menu = (AnchorPane) fxmlLoader.load();
            rootLayout.setCenter(menu);
            MenuController menuctrl = fxmlLoader.getController();
            menuctrl.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showReportMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/Report.fxml"));
            AnchorPane menu = (AnchorPane) fxmlLoader.load();
            rootLayout.setCenter(menu);
            ReportController reportController = fxmlLoader.getController();
            reportController.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showQueryMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/QueryStat.fxml"));
            SplitPane menu = (SplitPane) fxmlLoader.load();
            rootLayout.setCenter(menu);
            QueryController reportController = fxmlLoader.getController();
            reportController.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showClientMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/Client.fxml"));
            SplitPane clients = (SplitPane) fxmlLoader.load();

            rootLayout.setCenter(clients);
            ClientController clientController = fxmlLoader.getController();
            clientController.setMain(this);
            if (clients != null) {
                clientController.fillTable();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddClients() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/AddClient.fxml"));
            AnchorPane clients = (AnchorPane) fxmlLoader.load();

            rootLayout.setCenter(clients);

            ClientAddController clientAddController = fxmlLoader.getController();
            clientAddController.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEditClient(Client client) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/EditClient.fxml"));
            AnchorPane menu = (AnchorPane) fxmlLoader.load();

            rootLayout.setCenter(menu);

            ClientEditController clientEditController = fxmlLoader.getController();
            clientEditController.setMain(this);
            clientEditController.fillBoxes(client);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMasterMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/Master.fxml"));
            SplitPane clients = (SplitPane) fxmlLoader.load();

            rootLayout.setCenter(clients);

            MasterController masterController = fxmlLoader.getController();
            masterController.setMain(this);
            if (masters != null) {
                masterController.initialSet();
                masterController.fillTable();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddMasters() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/AddMaster.fxml"));
            AnchorPane masters = (AnchorPane) fxmlLoader.load();
            rootLayout.setCenter(masters);
            MasterAddController masterAddController = fxmlLoader.getController();
            masterAddController.setMain(this);
            masterAddController.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEditMaster(Master master) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/EditMaster.fxml"));
            AnchorPane menu = (AnchorPane) fxmlLoader.load();

            rootLayout.setCenter(menu);

            MasterEditController masterEditController = fxmlLoader.getController();
            masterEditController.setMain(this);
            masterEditController.fillBoxes(master);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOrderTypeMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/OrderType.fxml"));
            SplitPane menu = (SplitPane) fxmlLoader.load();

            rootLayout.setCenter(menu);

            OrderTypeController orderTypeController = fxmlLoader.getController();
            orderTypeController.setMain(this);
            orderTypeController.initialSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddOrderTypes() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/AddOrderType.fxml"));
            AnchorPane details = (AnchorPane) fxmlLoader.load();
            rootLayout.setCenter(details);
            OrderTypeAddController orderTypeAddController = fxmlLoader.getController();
            orderTypeAddController.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEditOrderType(OrderType orderType) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/EditOrderType.fxml"));
            AnchorPane menu = (AnchorPane) fxmlLoader.load();

            rootLayout.setCenter(menu);

            OrderTypeEditController orderTypeEditController = fxmlLoader.getController();
            orderTypeEditController.setMain(this);
            orderTypeEditController.fillBoxes(orderType);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCarMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/Car.fxml"));
            SplitPane cars = (SplitPane) fxmlLoader.load();
            rootLayout.setCenter(cars);
            CarController carController = fxmlLoader.getController();
            carController.setMain(this);
            if (cars != null) {
                carController.fillTable();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddCar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/AddCar.fxml"));
            AnchorPane cars = (AnchorPane) fxmlLoader.load();
            rootLayout.setCenter(cars);
            CarAddController carAddController = fxmlLoader.getController();
            carAddController.setMain(this);
            carAddController.init();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showEditCar(Car car) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/EditCar.fxml"));
            AnchorPane cars = (AnchorPane) fxmlLoader.load();
            rootLayout.setCenter(cars);
            CarEditController carEditController = fxmlLoader.getController();
            carEditController.setMain(this);
            carEditController.fillBoxes(car);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOrderMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/Order1.fxml"));
            SplitPane menu = (SplitPane) fxmlLoader.load();

            rootLayout.setCenter(menu);

            OrderController orderController = fxmlLoader.getController();
            orderController.setMain(this);
            if (getAllOrders() != null) {
                orderController.initialSet();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddOrder() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/AddOrder.fxml"));
            AnchorPane details = (AnchorPane) fxmlLoader.load();
            rootLayout.setCenter(details);
            OrderAddController orderAddController = fxmlLoader.getController();
            orderAddController.setMain(this);
            orderAddController.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEditOrder(Order1 order1) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/EditOrder.fxml"));
            AnchorPane menu = (AnchorPane) fxmlLoader.load();

            rootLayout.setCenter(menu);

            OrderEditController orderEditController = fxmlLoader.getController();
            orderEditController.setMain(this);
            orderEditController.init(order1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    *
    * Methods for work with clients
    *
    * */

    public ObservableList<String> getPassportsByName(String name) {
        ClientDAOImpl implem = new ClientDAOImpl();
        ObservableList<String> passports = FXCollections.observableArrayList(implem.getPassportsByName(name));
        return passports;
    }

    public ObservableList<Client> getAllClients() {
        ClientDAOImpl implem = new ClientDAOImpl();
        personData = FXCollections.observableArrayList(implem.getAll());
        return personData;
    }

    public ObservableList<Client> searchClientById(String id) {
        ClientDAOImpl implem = new ClientDAOImpl();
        personData = FXCollections.observableArrayList(implem.getByPassport(id));
        return personData;
    }

    public ObservableList<Client> searchClientByName(String name) {
        ClientDAOImpl implem = new ClientDAOImpl();
        personData = FXCollections.observableArrayList(implem.getByName(name));
        return personData;
    }

    public ObservableList<Client> searchClientByBirth(Date birth) {
        ClientDAOImpl implem = new ClientDAOImpl();
        personData = FXCollections.observableArrayList(implem.getByBirth(birth));
        return personData;
    }

    public ObservableList<Client> deleteClient(Client client) {
        ClientDAOImpl implem = new ClientDAOImpl();
        result = implem.deleteClient(client);
        personData = FXCollections.observableArrayList(getAllClients());
        return personData;
    }

    public Client getClientByPassport(String id) {
        ClientDAOImpl impl = new ClientDAOImpl();
        Client client = impl.getUniqueByPassport(id);
        return client;
    }

    public boolean getResult() {
        return result;
    }

    public boolean checkClientPassport(String id) {
        ClientDAOImpl implem = new ClientDAOImpl();
        return implem.checkByPassport(id);
    }

    public boolean addClient(String id, String name, Date birth) {
        Client client = new Client();
        client.setPassportId(id);
        client.setName(name);
        if (birth != null) client.setBirthday(new java.sql.Date(birth.getTime()));
        else {
            client.setBirthday(null);
        }
        ClientDAOImpl impl = new ClientDAOImpl();
        return impl.addClient(client);
    }

    public boolean updateClient(String id, String name, Date birth) {
        Client client = new Client();
        client.setPassportId(id);
        client.setName(name);
        client.setBirthday(new java.sql.Date(birth.getTime()));
        ClientDAOImpl impl = new ClientDAOImpl();
        return impl.updateClient(client);
    }

    public ObservableList<Client> getClientByUniqueName(String name) {
        ClientDAOImpl implem = new ClientDAOImpl();
        return personData = FXCollections.observableArrayList(implem.getByUniqueName(name));
    }
    /*
    *
    * Methods for work with order1 types
    *
    * */

    public ObservableList<OrderType> getAllOrderTypes() {
        OrderTypeDAOImpl implem = new OrderTypeDAOImpl();
        orderTypes = FXCollections.observableArrayList(implem.getAll());
        return orderTypes;
    }

    public ObservableList<OrderType> searchOrderTypeById(int id) {
        OrderTypeDAOImpl implem = new OrderTypeDAOImpl();
        orderTypes = FXCollections.observableArrayList(implem.getById(id));
        return orderTypes;
    }

    public ObservableList<OrderType> searchOrderTypeByName(String name) {
        OrderTypeDAOImpl implem = new OrderTypeDAOImpl();
        orderTypes = FXCollections.observableArrayList(implem.getByName(name));
        return orderTypes;
    }

    public OrderType getOrderTypeByName(String name) {
        OrderTypeDAOImpl implem = new OrderTypeDAOImpl();
        return implem.getUniqueByName(name);
    }

    public ObservableList<OrderType> searchOrderTypeByPrice(double price) {
        OrderTypeDAOImpl implem = new OrderTypeDAOImpl();
        orderTypes = FXCollections.observableArrayList(implem.getByPrice(price));
        return orderTypes;
    }

    public ObservableList<OrderType> deleteOrderType(OrderType master) {
        OrderTypeDAOImpl implem = new OrderTypeDAOImpl();
        result = implem.deleteOrderType(master);
        orderTypes = FXCollections.observableArrayList(getAllOrderTypes());
        return orderTypes;
    }

    public OrderType getOrderTypeById(int id) {
        OrderTypeDAOImpl impl = new OrderTypeDAOImpl();
        OrderType master = impl.getUniqueById(id);
        return master;
    }

    public boolean checkOrderTypeId(int id) {
        OrderTypeDAOImpl implem = new OrderTypeDAOImpl();
        return implem.checkById(id);
    }

    public boolean addOrderType(String name, double price) {
        OrderType orderType = new OrderType();
        orderType.setName(name);
        orderType.setPrice(price);
        OrderTypeDAOImpl impl = new OrderTypeDAOImpl();
        return impl.addOrderType(orderType);
    }

    public boolean updateOrderType(int id, String name, double price) {
        OrderType orderType = new OrderType();
        orderType.setTypeId(id);
        orderType.setName(name);
        orderType.setPrice(price);
        OrderTypeDAOImpl impl = new OrderTypeDAOImpl();
        return impl.updateOrderType(orderType);
    }

    public ObservableList<OrderType> filterOrderTypeByPrice(double value) {
        OrderTypeDAOImpl implem = new OrderTypeDAOImpl();
        orderTypes = FXCollections.observableArrayList(implem.filterPrice(value));
        return orderTypes;
    }

    public double getMinOrderTypePrice() {
        OrderTypeDAOImpl implem = new OrderTypeDAOImpl();
        double value = implem.minPrice();
        return value;
    }

    public double getMaxOrderTypePrice() {
        OrderTypeDAOImpl implem = new OrderTypeDAOImpl();
        double value = implem.maxPrice();
        return value;
    }

    /*
    *
    * Methods for work with masters
    *
    * */

    public ObservableList<Master> getAllMasters() {
        MasterDAOImpl implem = new MasterDAOImpl();
        masters = FXCollections.observableArrayList(implem.getAll());
        return masters;
    }

    public ObservableList<Master> getMastersByMin(String type) {
        int typeId = new OrderTypeDAOImpl().getUniqueByName(type).getTypeId();
        MasterDAOImpl masterDAO = new MasterDAOImpl();
        ObservableList<Master> mstr = FXCollections.observableArrayList(masterDAO.getAll());
        ObservableList<Master> res = FXCollections.observableArrayList(); // ???????? ???????? ???????
        boolean exit = false;
        for (int i = 0; i < new OrderDAOImpl().countFreeOrders(); i++) {
            for (Master m1 : mstr) {
                if (masterDAO.getCount(m1.getPassportId()) == i) {
                    res.add(m1);
                    exit = true;
                }
            }
            if (exit) break;
        }
        return res;
    }

    public ObservableList<Master> getFreeMasters(String type) {
        int typeId = new OrderTypeDAOImpl().getUniqueByName(type).getTypeId();
        MasterDAOImpl masterDAO = new MasterDAOImpl();
        ObservableList<Master> mstr = FXCollections.observableArrayList(searchMasterByCategory(type));
        ObservableList<Master> res = FXCollections.observableArrayList();
        boolean exit = false;
        int end = 1;
        if (new OrderDAOImpl().countFreeOrders() != 0) end = new OrderDAOImpl().countFreeOrders();
        for (int i = 0; i < end; i++) {
            for (Master m1 : mstr) {
                if (masterDAO.getCount(m1.getPassportId()) == i) {
                    res.add(m1);
                    exit = true;
                }
            }
            if (exit) break;
        }
        return res;
    }

    public ObservableList<Master> getBusyMasters(String type) {
        int typeId = new OrderTypeDAOImpl().getUniqueByName(type).getTypeId();
        MasterDAOImpl masterDAO = new MasterDAOImpl();
        ObservableList<Master> mstr = FXCollections.observableArrayList(searchMasterByCategory(type));
        ObservableList<Master> res = FXCollections.observableArrayList(); // ???????? ???????? ???????
        boolean exit = false;
        int start = 1;
        if (new OrderDAOImpl().countFreeOrders() != 0) start = new OrderDAOImpl().countFreeOrders();
        for (int i = start; i >= 1; i--) {
            for (Master m1 : mstr) {
                if (masterDAO.getCount(m1.getPassportId()) == i) {
                    res.add(m1);
                    exit = true;
                }
            }
            if (exit) break;
        }
        return res;
    }

    public ObservableList<Master> searchMasterById(String id) {
        MasterDAOImpl implem = new MasterDAOImpl();
        masters = FXCollections.observableArrayList(implem.getByPassport(id));
        return masters;
    }

    public ObservableList<Master> searchMasterByName(String name) {
        MasterDAOImpl implem = new MasterDAOImpl();
        masters = FXCollections.observableArrayList(implem.getByName(name));
        return masters;
    }

    public ObservableList<Master> searchMasterByBirth(Date birth) {
        MasterDAOImpl implem = new MasterDAOImpl();
        masters = FXCollections.observableArrayList(implem.getByBirth(birth));
        return masters;
    }

    public ObservableList<Master> searchMasterBySalary(double salary) {
        MasterDAOImpl implem = new MasterDAOImpl();
        masters = FXCollections.observableArrayList(implem.getBySalary(salary));
        return masters;
    }

    public ObservableList<Master> searchMasterByCommission(BigDecimal commission) {
        MasterDAOImpl implem = new MasterDAOImpl();
        masters = FXCollections.observableArrayList(implem.getByCommission(commission));
        return masters;
    }

    public ObservableList<Master> searchMasterByAcception(Date date) {
        MasterDAOImpl implem = new MasterDAOImpl();
        masters = FXCollections.observableArrayList(implem.getByAcception(date));
        return masters;
    }

    public ObservableList<Master> searchMasterByCategory(String category) {
        MasterDAOImpl implem = new MasterDAOImpl();
        masters = FXCollections.observableArrayList(implem.getByCategory(getOrderTypeByName(category).getTypeId()));
        return masters;
    }

    public ObservableList<Master> deleteMaster(Master master) {
        MasterDAOImpl implem = new MasterDAOImpl();
        result = implem.deleteMaster(master);
        masters = FXCollections.observableArrayList(getAllMasters());
        return masters;
    }

    public Master getMasterByPassport(String id) {
        MasterDAOImpl impl = new MasterDAOImpl();
        Master master = impl.getUniqueByPassport(id);
        return master;
    }

    public boolean checkMasterPassport(String id) {
        MasterDAOImpl implem = new MasterDAOImpl();
        return implem.checkByPassport(id);
    }

    public boolean addMaster(String id, String name, Date birth, Date adopt, String category, BigDecimal commission, double salary) {
        Master master = new Master();
        master.setPassportId(id);
        master.setName(name);
        master.setBirthday(new java.sql.Date(birth.getTime()));
        master.setAdoptionDate(new java.sql.Date(adopt.getTime()));
        master.setCategory(getOrderTypeByName(category).getTypeId());
        master.setOrderTypeByCategory(getOrderTypeById(getOrderTypeByName(category).getTypeId()));
        master.setCommission(commission);
        master.setSalary(salary);
        MasterDAOImpl impl = new MasterDAOImpl();
        return impl.addMaster(master);
    }

    public boolean updateMaster(String id, String name, Date birth, Date adopt, String category, BigDecimal commission, double salary, OrderType orderType) {
        Master master = new Master();
        master.setPassportId(id);
        master.setName(name);
        master.setBirthday(new java.sql.Date(birth.getTime()));
        master.setAdoptionDate(new java.sql.Date(adopt.getTime()));
        master.setCategory(getOrderTypeByName(category).getTypeId());
        master.setCommission(commission);
        master.setSalary(salary);
        master.setOrderTypeByCategory(orderType);
        MasterDAOImpl impl = new MasterDAOImpl();
        return impl.updateMaster(master);
    }

    public ObservableList<Master> filterMasterBySalary(double value) {
        MasterDAOImpl implem = new MasterDAOImpl();
        masters = FXCollections.observableArrayList(implem.filterSalary(value));
        return masters;
    }

    public ObservableList<Master> filterMasterByCommission(BigDecimal commission) {
        MasterDAOImpl implem = new MasterDAOImpl();
        masters = FXCollections.observableArrayList(implem.filterCommission(commission));
        return masters;
    }

    public double getMinSalary() {
        MasterDAOImpl implem = new MasterDAOImpl();
        double value = implem.minSalary();
        return value;
    }

    public double getMaxSalary() {
        MasterDAOImpl implem = new MasterDAOImpl();
        double value = implem.maxSalary();
        return value;
    }

    public BigDecimal getMaxCommission() {
        MasterDAOImpl implem = new MasterDAOImpl();
        BigDecimal value = implem.maxCommission();
        return value;
    }

    public BigDecimal getMinCommission() {
        MasterDAOImpl implem = new MasterDAOImpl();
        BigDecimal value = implem.minCommission();
        return value;
    }


    /*
    *
    * Methods for work with cars
    *
    * */

    public ObservableList<Car> getAllCars() {
        CarDAOImpl implem = new CarDAOImpl();
        cars = FXCollections.observableArrayList(implem.getAll());
        return cars;
    }

    public ObservableList<Car> searchCarById(String id) {
        CarDAOImpl implem = new CarDAOImpl();
        cars = FXCollections.observableArrayList(implem.getById(id));
        return cars;
    }

    public ObservableList<Car> searchCarByName(String name) {
        CarDAOImpl implem = new CarDAOImpl();
        cars = FXCollections.observableArrayList(implem.getByClientName(name));
        return cars;
    }

    public ObservableList<Car> searchCarByPassport(String id) {
        CarDAOImpl implem = new CarDAOImpl();
        cars = FXCollections.observableArrayList(implem.getByClientId(id));
        return cars;
    }

    public ObservableList<Car> searchCarByBrand(String name) {
        CarDAOImpl implem = new CarDAOImpl();
        cars = FXCollections.observableArrayList(implem.getByBrand(name));
        return cars;
    }

    public ObservableList<Car> searchCarByModel(String name) {
        CarDAOImpl implem = new CarDAOImpl();
        cars = FXCollections.observableArrayList(implem.getByModel(name));
        return cars;
    }

    public ObservableList<Car> searchCarByBrandModel(String brand, String model) {
        CarDAOImpl implem = new CarDAOImpl();
        cars = FXCollections.observableArrayList(implem.getByBrandModel(brand, model));
        return cars;
    }

    public ObservableList<Car> searchCarByYear(Date date) {
        CarDAOImpl implem = new CarDAOImpl();
        cars = FXCollections.observableArrayList(implem.getByYear(date));
        return cars;
    }

    public ObservableList<Car> deleteCar(Car car) {
        CarDAOImpl implem = new CarDAOImpl();
        result = implem.deleteCar(car);
        cars = FXCollections.observableArrayList(getAllCars());
        return cars;
    }

    public Car getCarById(String id) {
        CarDAOImpl impl = new CarDAOImpl();
        Car car = impl.getUniqueById(id);
        return car;
    }

    public boolean checkCarId(String id) {
        CarDAOImpl implem = new CarDAOImpl();
        return implem.checkById(id);
    }

    public boolean addCar(String id, String clientId, String brand, String model, Date year, Client client) {
        Car car = new Car();
        car.setCarId(id);
        car.setClientPassportId(clientId);
        car.setBrand(brand);
        car.setModel(model);
        if (year != null) car.setYear(new java.sql.Date(year.getTime()));
        else {
            car.setYear(null);
        }
        car.setClientByClientPassportId(client);
        CarDAOImpl impl = new CarDAOImpl();
        return impl.addCar(car);
    }

    public boolean updateCar(String id, String clientId, String brand, String model, Date year, Client client) {
        Car car = new Car();
        car.setCarId(id);
        car.setClientPassportId(clientId);
        car.setBrand(brand);
        car.setModel(model);
        if (year != null) car.setYear(new java.sql.Date(year.getTime()));
        else {
            car.setYear(null);
        }
        car.setClientByClientPassportId(client);
        CarDAOImpl impl = new CarDAOImpl();
        return impl.updateCar(car);
    }

     /*
    *
    * Methods for work with orders
    *
    * */

    public ObservableList<Order1> getAllOrders() {
        OrderDAOImpl implem = new OrderDAOImpl();
        orders = FXCollections.observableArrayList(implem.getAll());
        return orders;
    }

    public ObservableList<Order1> searchOrderById(int id) {
        OrderDAOImpl implem = new OrderDAOImpl();
        orders = FXCollections.observableArrayList(implem.getById(id));
        return orders;
    }

    public ObservableList<Order1> searchOrderByCarId(String id) {
        OrderDAOImpl implem = new OrderDAOImpl();
        orders = FXCollections.observableArrayList(implem.getByCarId(id));
        return orders;
    }

    public ObservableList<Order1> getOrderByCarName(String name) {
        OrderDAOImpl implem = new OrderDAOImpl();
        return orders = FXCollections.observableArrayList(implem.getByCarName(name));
    }

    public ObservableList<Order1> searchOrderByMasterId(String id) {
        OrderDAOImpl implem = new OrderDAOImpl();
        orders = FXCollections.observableArrayList(implem.getByMasterId(id));
        return orders;
    }

    public ObservableList<Order1> getOrderByMasterName(String name) {
        OrderDAOImpl implem = new OrderDAOImpl();
        return orders = FXCollections.observableArrayList(implem.getByMasterName(name));
    }

    public ObservableList<Order1> searchOrderByTypeId(int id) {
        OrderDAOImpl implem = new OrderDAOImpl();
        orders = FXCollections.observableArrayList(implem.getByTypeId(id));
        return orders;
    }

    public ObservableList<Order1> getOrderByTypeName(String name) {
        OrderDAOImpl implem = new OrderDAOImpl();
        return orders = FXCollections.observableArrayList(implem.getByTypeName(name));
    }

    public ObservableList<Order1> searchOrderByPrice(double price) {
        OrderDAOImpl implem = new OrderDAOImpl();
        orders = FXCollections.observableArrayList(implem.getByPrice(price));
        return orders;
    }

    public ObservableList<Order1> searchOrderByYear(Date date) {
        OrderDAOImpl implem = new OrderDAOImpl();
        orders = FXCollections.observableArrayList(implem.getByDate(date));
        return orders;
    }

    public ObservableList<Order1> deleteOrder(Order1 master) {
        OrderDAOImpl implem = new OrderDAOImpl();
        result = implem.deleteOrder(master);
        orders = FXCollections.observableArrayList(getAllOrders());
        return orders;
    }

    public Order1 getOrderById(int id) {
        OrderDAOImpl impl = new OrderDAOImpl();
        Order1 master = impl.getUniqueById(id);
        return master;
    }

    public boolean checkOrderId(int id) {
        OrderDAOImpl implem = new OrderDAOImpl();
        return implem.checkById(id);
    }

    public boolean addOrder(double price, Date date, String carId, String masterID, int typeId, Car car, Master master, OrderType type) {
        Order1 order1 = new Order1();
        if (date != null) order1.setDateRequest(new java.sql.Date(date.getTime()));
        else {
            order1.setDateRequest(null);
        }
        order1.setPrice(price);
        order1.setCarCarId(carId);
        order1.setMasterMasterId(masterID);
        order1.setOrderTypeId(typeId);
        order1.setCarByCarCarId(car);
        order1.setMasterByMasterMasterId(master);
        order1.setOrderTypeByOrderTypeId(type);
        order1.setExecuted(0);
        order1.setDateRequest(new java.sql.Date(date.getTime()));
        OrderDAOImpl impl = new OrderDAOImpl();
        return impl.addOrder(order1);
    }

    public ObservableList<Order1> getOrders() {
        return this.orders;
    }

    public boolean updateOrder(int id, double price, Date date, String carId, String masterID, int typeId,
                               Car car, Master master, OrderType type, int exec) {
        Order1 order1 = new Order1();
        if (date != null) order1.setDateRequest(new java.sql.Date(date.getDate()));
        else {
            order1.setDateRequest(null);
        }
        order1.setOrderId(id);
        order1.setPrice(price);
        order1.setCarCarId(carId);
        order1.setMasterMasterId(masterID);
        order1.setOrderTypeId(typeId);
        order1.setCarByCarCarId(car);
        order1.setMasterByMasterMasterId(master);
        order1.setOrderTypeByOrderTypeId(type);
        order1.setExecuted(exec);
        OrderDAOImpl impl = new OrderDAOImpl();
        return impl.updateOrder(order1);
    }

    public ObservableList<Order1> filterOrderByPrice(double value) {
        OrderDAOImpl implem = new OrderDAOImpl();
        orders = FXCollections.observableArrayList(implem.filterPrice(value));
        return orders;
    }

    public double getMinOrderPrice() {
        OrderDAOImpl implem = new OrderDAOImpl();
        double value = implem.minPrice();
        return value;
    }

    public double getMaxOrderPrice() {
        OrderDAOImpl implem = new OrderDAOImpl();
        double value = implem.maxPrice();
        return value;
    }

    public ObservableList<Order1> getExecuted() {
        OrderDAOImpl implem = new OrderDAOImpl();
        orders = FXCollections.observableArrayList(implem.getExec());
        return orders;
    }

    public ObservableList<Order1> getNotExecuted() {
        OrderDAOImpl implem = new OrderDAOImpl();
        orders = FXCollections.observableArrayList(implem.getNotExec());
        return orders;
    }

    public int getLastOrderIndex() {
        OrderDAOImpl implem = new OrderDAOImpl();
        int res = implem.getLastId();
        return res;
    }

    /*
    *
    * Methods for work with reports
    *
    * */

    public void masterReport() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample sheet");
        Object[] header = new Object[]{"Паспорт мастера", "ФИО", "День рождения", "Дата принятия", "Категория", "Коммиссия", "Зарплата"};

        Map<String, Object> data = new HashMap<String, Object>();

        int k = 1;
        for (Master master : getAllMasters()) {
            data.put(String.valueOf(k++), master);
        }
        Set<String> keyset = data.keySet();
        int rownum = 0;

        Row row = sheet.createRow(rownum++);
        int cellnum = 0;
        for (Object obj : header) {
            Cell cell = row.createCell(cellnum++);
            cell.setCellValue((String) obj);
        }

        for (String key : keyset) {

            cellnum = 0;
            row = sheet.createRow(rownum++);
            Master master = (Master) data.get(key);
            Cell cell = row.createCell(cellnum++);
            cell.setCellValue(master.getPassportId());
            cell = row.createCell(cellnum++);
            cell.setCellValue(master.getName());
            cell = row.createCell(cellnum++);
            cell.setCellValue(master.getBirthday().toString());
            cell = row.createCell(cellnum++);
            cell.setCellValue(master.getAdoptionDate().toString());
            cell = row.createCell(cellnum++);
            cell.setCellValue(master.getOrderTypeByCategory().getName());
            cell = row.createCell(cellnum++);
            cell.setCellValue(master.getCommission().toString());
            cell = row.createCell(cellnum++);
            cell.setCellValue(master.getSalary());

        }
        try {
            FileOutputStream out =
                    new FileOutputStream(new File("D:\\Reports\\MastersInfo.xls"));
            workbook.write(out);
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void masterOrdersReport(String id) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Заказы по мастеру");
        Object[] header = new Object[]{"№ заказа", "Дата заказа", "№ авто", "Услуга", "Цена", "Состояние"};
        Map<String, Object> data = new HashMap<String, Object>();
        int k = 1;
        for (Order1 order1 : searchOrderByMasterId(id)) {
            data.put(String.valueOf(k++), order1);
        }
        Set<String> keyset = data.keySet();
        int rownum = 0;
        int cellnum = 0;
        Row row = sheet.createRow(rownum++);
        Cell cell = row.createCell(0);
        cell.setCellValue("Статистика мастера " + getMasterByPassport(id).getName() + ", паспорт " + id);
        row = sheet.createRow(rownum++);
        for (Object obj : header) {
            cell = row.createCell(cellnum++);
            cell.setCellValue((String) obj);
        }
        for (String key : keyset) {
            cellnum = 0;
            row = sheet.createRow(rownum++);
            Order1 order1 = (Order1) data.get(key);
            cell = row.createCell(cellnum++);
            cell.setCellValue(order1.getOrderId());
            cell = row.createCell(cellnum++);
            cell.setCellValue(order1.getDateRequest().toString());
            cell = row.createCell(cellnum++);
            cell.setCellValue(order1.getCarCarId());
            cell = row.createCell(cellnum++);
            cell.setCellValue(order1.getOrderTypeByOrderTypeId().getName());
            cell = row.createCell(cellnum++);
            cell.setCellValue(order1.getPrice());
            cell = row.createCell(cellnum++);
            if(order1.getExecuted() == 0) cell.setCellValue("Выполнено");
            else cell.setCellValue("Невыполнено");
        }
        try {
            FileOutputStream out =
                    new FileOutputStream(new File("D:\\Reports\\MasterOrderInfo.xls"));
            workbook.write(out);
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clientCarReport(String id) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Авто по клиенту");
        Object[] header = new Object[]{"№ авто", "Марка", "Модель", "Дата покупки"};
        Map<String, Object> data = new HashMap<String, Object>();
        int k = 1;
        for (Car car : searchCarByPassport(id)) {
            data.put(String.valueOf(k++), car);
        }
        Set<String> keyset = data.keySet();
        int rownum = 0;
        int cellnum = 0;
        Row row = sheet.createRow(rownum++);
        Cell cell = row.createCell(0);
        cell.setCellValue("Авто клиента " + getClientByPassport(id).getName() + ", паспорт " + id);
        row = sheet.createRow(rownum++);
        for (Object obj : header) {
            cell = row.createCell(cellnum++);
            cell.setCellValue((String) obj);
        }
        for (String key : keyset) {
            cellnum = 0;
            row = sheet.createRow(rownum++);
            Car car = (Car) data.get(key);
            cell = row.createCell(cellnum++);
            cell.setCellValue(car.getCarId());
            cell = row.createCell(cellnum++);
            cell.setCellValue(car.getBrand());
            cell = row.createCell(cellnum++);
            cell.setCellValue(car.getModel());
            cell = row.createCell(cellnum++);
            cell.setCellValue(car.getYear().toString());
        }
        try {
            FileOutputStream out =
                    new FileOutputStream(new File("D:\\Reports\\ClientCarInfo.xls"));
            workbook.write(out);
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public List<HashMap> executeQuery(String query) throws ValidationException {
            QueryImpl impl = new QueryImpl();
            List<HashMap> result = null;
            try {
                result = impl.executeQuery(query);
            } catch (SQLGrammarException e) {
                Dialogs.create()
                        .title("Alert")
                        .masthead("Wrong SQL syntax.")
                        .showWarning();
            } catch (NonUniqueDiscoveredSqlAliasException e) {
                Dialogs.create()
                        .title("Alert")
                        .masthead("Unique aliases must be specified for arguments with the same title.")
                        .showWarning();
            } catch (GenericJDBCException e) {
                Dialogs.create()
                        .title("Alert")
                        .masthead("You can use only SELECT commands.")
                        .showWarning();
            }
            return result;
    }
}
