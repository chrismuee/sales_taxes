package my.project.salestaxes.taxation;

public enum TaxFactory {

  BASIC_SALES_TAX {
    @Override
    public SalesTax createInstance() {
      return new BasicSalesTax();
    }
  },
  IMPORT_DUTY_TAX {
    @Override
    public SalesTax createInstance() {
      return new ImportDutyTax();
    }
  };

  public abstract SalesTax createInstance();
}
