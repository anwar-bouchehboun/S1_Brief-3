package org.BatiCuisine.CouchePresentation.controller;

import org.BatiCuisine.CoucheMetier.Entite.Client;
import org.BatiCuisine.CoucheMetier.Entite.Mainœuvre;
import org.BatiCuisine.CoucheMetier.Entite.Projet;
import org.BatiCuisine.CoucheMetier.Enum.EtatProjet;
import org.BatiCuisine.CoucheMetier.Enum.TypeComposant;
import org.BatiCuisine.CouchePresentation.CostumColor;
import org.BatiCuisine.coucheServices.ClientService;
import org.BatiCuisine.coucheServices.MainoeuvreServices;
import org.BatiCuisine.coucheUtilitaire.InputValidator;
import org.BatiCuisine.coucheUtilitaire.LoggerMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ClientController {


    public ClientController(){

    }
        public final ClientService clientService = new ClientService();
        public  final MainoeuvreServices mainoeuvreServices=new MainoeuvreServices();
        public static Projet projet=new Projet();
         List<Mainœuvre> mainœuvres = new ArrayList<>();

    //MenuStart
    public void menuStart(){
        do {
            menuClient();
            int choix=InputValidator.getIntInput("Entre choix : ");
            switch (choix){
                case 1:
                    crateProject();
                    break;
                case 2:
                    createProjectRecherche();
                    break;
                case 3:  System.out.println(CostumColor.PURPLE_BOLD_BRIGHT + "-----_____Exit Menu_______------" + CostumColor.RESET);
                    return;
                default:
                    System.out.println(CostumColor.RED_BOLD_BRIGHT + "Invalid choice");
                    break;

            }
        }while (true);
    }
    public Client createClient() {
        String nom = InputValidator.getStringInput("Entre Nom Client :");
        String adresse = InputValidator.getStringInput("Entre Adresse : ");
        String tele = InputValidator.getIntInputNombre("Entre Telephone +212|0 :");
        boolean isProfessional = InputValidator.getBooleanInput("Entre isProfessional (1-true,2-false) :");
        Client client =new Client();
        client.setNom(nom);
        client.setAdrresse(adresse);
        client.setEstProfessionnel(isProfessional);
        client.setTelephone(tele);

        LoggerMessage.info("Client registration successful.");
     return    clientService.createClient(client);

    }

    //findClient
    public Client findClient(Client client){
        List<Client> clients = clientService.Client();
        Optional<Client> opClient = clients.stream()
                .filter(t -> t.getNom().equals(client.getNom()))
                .findFirst();
        if (opClient.isPresent()) {
            Client foundClient = opClient.get();
            System.out.println(foundClient);
            return foundClient;
        } else {
            LoggerMessage.error("Aucun Client");
            return null;
        }

    }
//check Client for add projet
    public void checkOptionalClient(Client client){
        Optional<Client> clientOpt = Optional.ofNullable(client);

        clientOpt.ifPresentOrElse(p -> {
            boolean addProject = InputValidator.getBooleanInput("Voulez-vous ajouter un projet pour ce client? (1: Oui, 2: Non) :");

            if (addProject) {
           Projet p1=  inputProjet(p);
                LoggerMessage.info("Projet registration successful.");
                createMainoeuvre(p1);


            } else {
                LoggerMessage.info("Aucun projet ajouté pour le client.");
            }
        }, () -> LoggerMessage.error("Aucun Client"));

    }
//create Project and client
    public  void  crateProject(){
        Client c= createClient();
        checkOptionalClient(c);


    }
    //Reche client add Client
    public void createProjectRecherche(){
        String nom =InputValidator.getStringInput("Entre NOM RECHERCHE :");
       Client client = new Client();
       client.setNom(nom);
      Client cl=  findClient(client);
      if(cl!=null){
          checkOptionalClient(cl);
      }else {
          crateProject();
      }





    }
    public void menuClient(){
        System.out.println(CostumColor.BROWN_BACKGROUND+CostumColor.WHITE_BOLD_BRIGHT+"----------------------------------------------------------- "+ CostumColor.RESET);
        System.out.println(CostumColor.BLUE_BOLD_BRIGHT+"|Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?  : " + CostumColor.RESET);
        System.out.println("|Appuyez sur 1 pour" +CostumColor.PURPLE_BOLD_BRIGHT +"|• Ajouter un nouveau client    "      + CostumColor.RESET);
        System.out.println("|Appuyez sur 2 pour" +CostumColor.PURPLE_BOLD_BRIGHT +"|• Chercher un client existant  "      + CostumColor.RESET);
        System.out.println("|Appuyez sur 3 pour" +CostumColor.PURPLE_BOLD_BRIGHT +"| _____Quitter__________"+ CostumColor.RESET);
        System.out.println(CostumColor.BROWN_BACKGROUND+CostumColor.WHITE_BOLD_BRIGHT+"----------------------------------------------------------- "+ CostumColor.RESET);
        System.out.println("Choix => : " +CostumColor.PURPLE_BOLD_BRIGHT + " CHOIX "+ CostumColor.RESET);
    }





    //create Projet
    public Projet inputProjet(Client client){
        System.out.println(CostumColor.PURPLE_BOLD_BRIGHT +" Create Projet  "      + CostumColor.RESET);
     String nomProjet=InputValidator.getStringInput("Entrez le nom du projet:");
      double sourface= InputValidator.getDoubleInput("Entrez la surface de la cuisine (en m²) : :");
        projet.setNomProjet(nomProjet);
        projet.setSurface(sourface);
        projet.setEtatProjet(EtatProjet.ENCOURS);
        projet.setClient(client);
        clientService.createProjet(projet);
       return  projet;
    }

    public void createMainoeuvre(Projet p) {
       if( InputValidator.askYesNoQuestion("Voulez-vous ajouter un Composant yes - non ?")){
           mainOeuvre(p);
       }else{
          LoggerMessage.warn("Ajout refusé.");

       }

    }

    public  void mainOeuvre(Projet p){
        String continueInput;
        do {
            Mainœuvre mainœuvre = new Mainœuvre();

            String name = InputValidator.getStringInput("Entrez le type de main-d'œuvre (e.g., Ouvrier de base, Spécialiste): ");
            mainœuvre.setNom(name);
            mainœuvre.setTypeComposant("MAINOEUVRE");
            mainœuvre.setProjet(p);

            double tauxHoraire = InputValidator.getDoubleInput("Entrez le taux horaire de cette main-d'œuvre (€/h): ");
            mainœuvre.setTauxHoraire(tauxHoraire);

            double heuresTravail = InputValidator.getDoubleInput("Entrez le nombre d'heures travaillées: ");
            mainœuvre.setHeuresTravail(heuresTravail);

            double productivite = InputValidator.getDouble("Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité): ");
            mainœuvre.setProductiviteOuvrier(productivite);

            mainœuvres.add(mainœuvre);

            continueInput = InputValidator.getYesNoInput("Voulez-vous ajouter un autre? (yes/no): ");

        } while (continueInput.equalsIgnoreCase("yes"));

        mainœuvres.forEach(mainœuvre -> mainoeuvreServices.createMainoeuvre(mainœuvre));

        LoggerMessage.info(CostumColor.BROWN_BACKGROUND+CostumColor.WHITE_BOLD_BRIGHT+"Main-d'œuvre ajoutée avec succès ! "+ CostumColor.RESET);
    }

}


