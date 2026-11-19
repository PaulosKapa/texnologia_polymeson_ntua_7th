package models;
//κλάση για μπαταρίες
public class Battery extends Item {
    double pmax;
    double eff;

    public Battery(String id,
                String type,
                double capex,
                double size,
                int life,
                double pmax,
                double eff){
                    super(id, type, capex, size, life);
                    this.pmax = pmax;
                    this.eff = eff;
                }
    
    public double getPmax(){
        return pmax;
    }
    
    public double getEff(){
        return eff;
    }

    public String getDetails() {
        return super.getDetails() + "\nPmax: " + getPmax() + "\nEff: " + getEff();
    }
}
