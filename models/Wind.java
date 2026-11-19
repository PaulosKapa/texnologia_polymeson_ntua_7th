package models;
//κλάση για ανεμογεννήτριες
public class Wind extends Item {
    double cutin, rated, cutout;
    

    public Wind(String id,
                String type,
                double capex,
                double size,
                int life,
                double cutin,
                double rated, 
                double cutout){
                    super(id, type, capex, size, life);
                    this.cutin = cutin;
                    this.rated = rated;
                    this.cutout = cutout;
                }
    
    public double getCutin(){
        return cutin;
    }
    
    public double getRated(){
        return rated;
    }

    public double getCutout(){
        return cutout;
    }

    public String getDetails() {
        return super.getDetails() + "Cutin: " + getCutin() + "Rated: " + getRated() + "Cutout: " + getCutout();
    }
}
