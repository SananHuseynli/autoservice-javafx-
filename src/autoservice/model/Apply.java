package autoservice.model;


public class Apply {
   private Integer id;
   private Customer customer;
   private Status status;
   private Service service;
   private Service price;


   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Customer getCustomer() {
      return customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
   }

   public Status getStatus() {
      return status;
   }

   public void setStatus(Status status) {
      this.status = status;
   }

   public Service getService() {
      return service;
   }

   public void setService(Service service) {
      this.service = service;
   }

   public Service getPrice() {
      return price;
   }

   public void setPrice(Service price) {
      this.price = price;
   }

   @Override
   public String toString() {
      return "Apply{" +
              "id=" + id +
              ", customer=" + customer +
              ", status=" + status +
              ", service=" + service +
              ", price=" + price +
              '}';
   }


}