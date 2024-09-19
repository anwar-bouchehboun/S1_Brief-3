package org.BatiCuisine.CoucheMetier.Entite;

import org.BatiCuisine.CoucheMetier.Enum.TypeComposant;

public class Mainœuvre extends  Composant {
    private  double tauxHoraire;
    private double heuresTravail;
    private  double productiviteOuvrier;

    public Mainœuvre() {
    }

    public Mainœuvre(Integer id, String nom, TypeComposant typeComposant, double tauxTva,Projet projet, double tauxHoraire, double heuresTravail, double productiviteOuvrier) {
        super(id, nom, typeComposant, tauxTva,projet);
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
    }

    public double getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public double getHeuresTravail() {
        return heuresTravail;
    }

    public void setHeuresTravail(double heuresTravail) {
        this.heuresTravail = heuresTravail;
    }

    public double getProductiviteOuvrier() {
        return productiviteOuvrier;
    }

    public void setProductiviteOuvrier(double productiviteOuvrier) {
        this.productiviteOuvrier = productiviteOuvrier;
    }

    @Override
    public double calculerTotal() {
        double baseCost = tauxHoraire * heuresTravail;
        double productivityAdjustment = baseCost * (productiviteOuvrier / 100.0);

        return baseCost + productivityAdjustment;
    }
}
