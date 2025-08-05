package com.FabulasDeSapo.AdventureForge.controller;

import com.FabulasDeSapo.AdventureForge.actions.BattleAction;
import com.FabulasDeSapo.AdventureForge.actions.BattleResult;
import com.FabulasDeSapo.AdventureForge.domain.Enemy;
import com.FabulasDeSapo.AdventureForge.domain.Hero;
import com.FabulasDeSapo.AdventureForge.enums.CharacterType;
import com.FabulasDeSapo.AdventureForge.enums.Item;
import com.FabulasDeSapo.AdventureForge.enums.MissionReward;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.*;
import com.google.firebase.auth.UserInfo;
import java.util.concurrent.ExecutionException;
import com.FabulasDeSapo.AdventureForge.enums.Category;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api")

@CrossOrigin(origins = "http://localhost:4200")
public class FirebaseController {

    @GetMapping("/firestore")
    public String testFirestore() {
        try {
            Firestore db = FirestoreClient.getFirestore();
            return "✅";
        } catch (Exception e) {
            e.printStackTrace();
            return " error al conectar con el front ❌";
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody Map<String, Object> userData) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            String userEmail = (String) userData.get("email");


            DocumentReference userDoc   = db.collection("users").document(userEmail);
            DocumentReference craftersDocRef = db.collection("crafters").document(userEmail);
            DocumentReference weaponsmith   = craftersDocRef.collection("usercrafters").document("1.weaponsmith");
            DocumentReference armorsmith   = craftersDocRef.collection("usercrafters").document("2.armorsmith");
            DocumentReference carpenter   = craftersDocRef.collection("usercrafters").document("3.carpenter");
            DocumentReference tailor   = craftersDocRef.collection("usercrafters").document("4.tailor");
            DocumentReference alchemist   = craftersDocRef.collection("usercrafters").document("5.alchemist");
            DocumentReference map1Doc   = userDoc.collection("maps").document("map1");
            DocumentReference map2Doc   = userDoc.collection("maps").document("map2");

            Map<String, Object> weaponsmithData = new HashMap<>();
            weaponsmithData.put("exp", 0);
            weaponsmithData.put("level", 1);
            weaponsmithData.put("maxExp", 1000);
            weaponsmithData.put("type", "WEAPONSMITH");
            weaponsmith.set(weaponsmithData).get();

            Map<String, Object> armorsmithData = new HashMap<>();
            armorsmithData.put("exp", 0);
            armorsmithData.put("level", 1);
            armorsmithData.put("maxExp", 1000);
            armorsmithData.put("type", "ARMORSMITH");
            armorsmith.set(armorsmithData).get();

            Map<String, Object> carpenterData = new HashMap<>();
            carpenterData.put("exp", 0);
            carpenterData.put("level", 1);
            carpenterData.put("maxExp", 1000);
            carpenterData.put("type", "CARPENTER");
            carpenter.set(carpenterData).get();

            Map<String, Object> tailorData = new HashMap<>();
            tailorData.put("exp", 0);
            tailorData.put("level", 1);
            tailorData.put("maxExp", 1000);
            tailorData.put("type", "TAILOR");
            tailor.set(tailorData).get();

            Map<String, Object> alchemistData = new HashMap<>();
            alchemistData.put("exp", 0);
            alchemistData.put("level", 1);
            alchemistData.put("maxExp", 1000);
            alchemistData.put("type", "ALCHEMIST");
            alchemist.set(alchemistData).get();

            Map<String, Object> map1Data = new HashMap<>();
            map1Data.put("M_ENT1", 0);
            map1Data.put("M_FOREST1", 0);
            map1Data.put("M_GOLEM1", 0);
            map1Data.put("M_JABALI1", 0);
            map1Data.put("M_JABALI2", 0);
            map1Data.put("M_JABALI3", 0);
            map1Data.put("M_JABALI4", 0);
            map1Data.put("M_JABALI5", 0);
            map1Data.put("M_JABALI6", 0);
            map1Data.put("M_JABALI7", 0);
            map1Doc.set(map1Data).get();


            return ResponseEntity.ok(userData);

        } catch (InterruptedException | ExecutionException e) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "Error al agregar el usuario ❌");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
        }
    }


    @PostMapping("/addUserLog")
    public ResponseEntity<Map<String, Object>> addUserLog(@RequestBody Map<String, Object> userLog) {
        Map<String, Object> response = new HashMap<>();
        try {
            Firestore db = FirestoreClient.getFirestore();
            String userEmail = (String) userLog.get("email");

            if (userEmail == null || userEmail.isEmpty()) {
                response.put("error", "El email es obligatorio ❌");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // 🕒 Formatear la fecha en "January 31, 2025 at 7:04:37 PM UTC+1"
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy 'a las [' h:mm:ss a ']'");
            sdf.setTimeZone(TimeZone.getTimeZone("Europe/Madrid")); // ⬅️ Asegura UTC+1

            String formattedDate = sdf.format(new Date());

            // Guardar la fecha con formato UTC+1 en Firestore
            userLog.put("LogDate", formattedDate);

            DocumentReference docRef = db.collection("lastLogs").document(userEmail);
            WriteResult result = docRef.set(userLog).get();

            response.put("message", "Log agregado con éxito");
            response.put("timestamp", result.getUpdateTime());
            return ResponseEntity.ok(response);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            response.put("error", "Error al agregar el log ❌");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/addTesterReport")
    public ResponseEntity<Map<String, Object>> addTesterReport(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        try {
            Firestore db = FirestoreClient.getFirestore();

            DocumentReference docRef = db.collection("reports").document();
            WriteResult result = docRef.set(data).get();

            response.put("message", "reporte de incidencia agregado con éxito");
            return ResponseEntity.ok(response);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            response.put("error", "Error al agregar el reporte de incidencia ❌");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/getHerosData/{email}")
    public ResponseEntity<List<Map<String, Object>>> getHerosData(
            @PathVariable("email") String email,
            @RequestParam(value = "heroId", required = false) String heroId
    ) {
        List<Map<String, Object>> heroesList = new ArrayList<>();

        try {
            Firestore db = FirestoreClient.getFirestore();
            List<DocumentSnapshot> documents;

            if (heroId != null && !heroId.isEmpty()) {
                DocumentReference docRef = db.collection("heros").document(email).collection("userheros").document(heroId);
                DocumentSnapshot snapshot = docRef.get().get();
                if (!snapshot.exists()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
                documents = Collections.singletonList(snapshot);
            } else {
                CollectionReference userHerosRef = db.collection("heros").document(email).collection("userheros");
                ApiFuture<QuerySnapshot> future = userHerosRef.get();
                documents = new ArrayList<>(future.get().getDocuments());
            }

            for (DocumentSnapshot doc : documents) {
                Map<String, Object> heroData = doc.getData();
                heroData.put("id", doc.getId()); // Añadir el ID del documento
                CharacterType type = CharacterType.valueOf(String.valueOf(heroData.get("Type")));
                String[] bonus = Arrays.stream(type.bonus).map(Category::name).toArray(String[]::new);
                String[] collateral = Arrays.stream(type.collateral).map(Category::name).toArray(String[]::new);
                heroData.put("Bonus", bonus);
                heroData.put("Collateral", collateral);
                // Obtener referencias a BaseStats, Equipment y EquipmentStats
                DocumentReference baseStatsRef = doc.getReference().collection("Details").document("BaseStats");
                DocumentReference equipmentRef = doc.getReference().collection("Details").document("Equipment");

                DocumentSnapshot baseStatsSnapshot = baseStatsRef.get().get();
                DocumentSnapshot equipmentSnapshot = equipmentRef.get().get();

                if (baseStatsSnapshot.exists() && equipmentSnapshot.exists()) {
                    Map<String, Object> baseStats = baseStatsSnapshot.getData();
                    heroData.put("Dbrutal", baseStats.getOrDefault("s0_Dbrutal", 0));
                    heroData.put("Dletal", baseStats.getOrDefault("s1_Dletal", 0));
                    heroData.put("Dmistic", baseStats.getOrDefault("s2_Dmistic", 0));
                    heroData.put("armor", baseStats.getOrDefault("s3_armor", 0));
                    heroData.put("resistance", baseStats.getOrDefault("s4_resistance", 0));
                    heroData.put("accuracy", baseStats.getOrDefault("s5_accuracy", 0));
                    heroData.put("evasion", baseStats.getOrDefault("s6_evasion", 0));
                    heroData.put("critic", baseStats.getOrDefault("s7_critic", 0));
                    heroData.put("maxHealth", baseStats.getOrDefault("s8_maxHealth", 0));
                    heroData.put("maxExp", baseStats.getOrDefault("s9_maxExp", 0));



                    Map<String, Object> equipment = equipmentSnapshot.getData();
                    String Boots = String.valueOf(equipment.get("Boots"));
                    String Chest = String.valueOf(equipment.get("Chest"));
                    String Gloves = String.valueOf(equipment.get("Gloves"));
                    String Helmet = String.valueOf(equipment.get("Helmet"));
                    String Jewel = String.valueOf(equipment.get("Jewel"));
                    String Weapon = String.valueOf(equipment.get("Weapon"));

                    heroData.put("Boots", new Object[]{Boots,0,0,0,0,0,0,0,0,0});
                    heroData.put("Chest", new Object[]{Chest,0,0,0,0,0,0,0,0,0});
                    heroData.put("Gloves", new Object[]{Gloves,0,0,0,0,0,0,0,0,0});
                    heroData.put("Helmet", new Object[]{Helmet,0,0,0,0,0,0,0,0,0});
                    heroData.put("Jewel", new Object[]{Jewel,0,0,0,0,0,0,0,0,0});
                    heroData.put("Weapon", new Object[]{Weapon,0,0,0,0,0,0,0,0,0});


                    List<String> equipmentSlots = Arrays.asList("Boots", "Chest", "Gloves", "Helmet", "Jewel", "Weapon");

                    int dBrutal = 0, dLetal = 0, dMistic = 0;
                    int armor = 0, resistance = 0, accuracy = 0;
                    int evasion = 0, critic = 0, maxHealth = 0;

                    for (String slot : equipmentSlots) {
                        Object itemValue = equipment.get(slot);
                        if (itemValue instanceof String) {
                            String itemKey = ((String) itemValue).trim();
                            if (!itemKey.equalsIgnoreCase("NULL") && !itemKey.isEmpty()) {
                                try {
                                    Item item = Item.valueOf(itemKey);

                                    heroData.put(slot, new Object[]{
                                            itemKey,
                                            item.attributes.Dbrutal,
                                            item.attributes.Dletal,
                                            item.attributes.Dmistic,
                                            item.attributes.armor,
                                            item.attributes.resistance,
                                            item.attributes.accuracy,
                                            item.attributes.evasion,
                                            item.attributes.critic,
                                            item.attributes.maxHealth
                                    });
                                    heroData.put(slot+"Subtype", String.valueOf(item.subtype));
                                    List<Number> bonusItemArray = Arrays.asList(
                                            item.subtype.benefits.Dbrutal,
                                            item.subtype.benefits.Dletal,
                                            item.subtype.benefits.Dmistic,
                                            item.subtype.benefits.armor,
                                            item.subtype.benefits.resistance,
                                            item.subtype.benefits.accuracy,
                                            item.subtype.benefits.evasion,
                                            item.subtype.benefits.critic,
                                            item.subtype.benefits.maxHealth
                                    );
                                    heroData.put(slot+"Bonus", bonusItemArray);
                                    List<Number> collateralItemArray = Arrays.asList(
                                            item.subtype.collateral.Dbrutal,
                                            item.subtype.collateral.Dletal,
                                            item.subtype.collateral.Dmistic,
                                            item.subtype.collateral.armor,
                                            item.subtype.collateral.resistance,
                                            item.subtype.collateral.accuracy,
                                            item.subtype.collateral.evasion,
                                            item.subtype.collateral.critic,
                                            item.subtype.collateral.maxHealth
                                    );
                                    heroData.put(slot+"Collateral", collateralItemArray);

                                    dBrutal    += item.attributes.Dbrutal;
                                    dLetal     += item.attributes.Dletal;
                                    dMistic    += item.attributes.Dmistic;
                                    armor      += item.attributes.armor;
                                    resistance += item.attributes.resistance;
                                    accuracy   += item.attributes.accuracy;
                                    evasion    += item.attributes.evasion;
                                    critic     += item.attributes.critic;
                                    maxHealth  += item.attributes.maxHealth;

                                } catch (IllegalArgumentException e) {
                                    System.err.println("Item no válido en slot " + slot + ": " + itemKey);
                                }
                            }
                        }
                    }

                    heroData.put("Items_Dbrutal", dBrutal);
                    heroData.put("Items_Dletal", dLetal);
                    heroData.put("Items_Dmistic", dMistic);
                    heroData.put("Items_armor", armor);
                    heroData.put("Items_resistance", resistance);
                    heroData.put("Items_accuracy", accuracy);
                    heroData.put("Items_evasion", evasion);
                    heroData.put("Items_critic", critic);
                    heroData.put("Items_maxHealth", maxHealth);

                } else {
                    System.out.println("Los apartados de BaseStats o Equipment no existen.");
                }

                heroesList.add(heroData);
            }

            return ResponseEntity.ok(heroesList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/getItemsData/{email}")
    public ResponseEntity<List<Map<String, Object>>> getItemsData(
            @PathVariable("email") String email,
            @RequestParam(value = "type", required = false) String type) {

        List<Map<String, Object>> itemsList = new ArrayList<>();
        try {
            Firestore db = FirestoreClient.getFirestore();
            CollectionReference userItemsRef = db.collection("storage").document(email).collection("inventory");

            Query query = userItemsRef;
            if (type != null && !type.isEmpty()) {
                query = userItemsRef.whereEqualTo("type", type);
            }

            ApiFuture<QuerySnapshot> future = query.get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                Map<String, Object> itemData = doc.getData();
                itemData.put("id", doc.getId());

                // Obtener la referencia a BaseStats
                DocumentReference baseStatsRef = doc.getReference().collection("Attributes").document("BaseStats");
                DocumentReference BonusRef = doc.getReference().collection("Attributes").document("Bonus");
                DocumentReference CollateralRef = doc.getReference().collection("Attributes").document("Collateral");
                DocumentSnapshot baseStatsSnapshot = baseStatsRef.get().get();
                DocumentSnapshot BonusSnapshot = BonusRef.get().get();
                DocumentSnapshot CollateralSnapshot = CollateralRef.get().get();

                if (baseStatsSnapshot.exists()) {
                    Map<String, Object> baseStats = baseStatsSnapshot.getData();

                    itemData.put("Dbrutal", baseStats.getOrDefault("s0_Dbrutal", 0));
                    itemData.put("Dletal", baseStats.getOrDefault("s1_Dletal", 0));
                    itemData.put("Dmistic", baseStats.getOrDefault("s2_Dmistic", 0));
                    itemData.put("armor", baseStats.getOrDefault("s3_armor", 0));
                    itemData.put("resistance", baseStats.getOrDefault("s4_resistance", 0));
                    itemData.put("accuracy", baseStats.getOrDefault("s5_accuracy", 0));
                    itemData.put("evasion", baseStats.getOrDefault("s6_evasion", 0));
                    itemData.put("critic", baseStats.getOrDefault("s7_critic", 0));
                    itemData.put("maxHealth", baseStats.getOrDefault("s8_maxHealth", 0));


                } else {
                    System.out.println("El documento BaseStats no existe.");
                }
                if (BonusSnapshot.exists()) {
                    Map<String, Object> bonusStats = BonusSnapshot.getData();
                    List<Number> bonusArray = Arrays.asList(
                            (Number) bonusStats.getOrDefault("s0_Dbrutal", 0),
                            (Number) bonusStats.getOrDefault("s1_Dletal", 0),
                            (Number) bonusStats.getOrDefault("s2_Dmistic", 0),
                            (Number) bonusStats.getOrDefault("s3_armor", 0),
                            (Number) bonusStats.getOrDefault("s4_resistance", 0),
                            (Number) bonusStats.getOrDefault("s5_accuracy", 0),
                            (Number) bonusStats.getOrDefault("s6_evasion", 0),
                            (Number) bonusStats.getOrDefault("s7_critic", 0),
                            (Number) bonusStats.getOrDefault("s8_maxHealth", 0)
                    );
                    itemData.put("itemBonus", bonusArray);
                } else {
                    System.out.println("El documento Bonus no existe.");
                }
                if (CollateralSnapshot.exists()) {
                    Map<String, Object> CollateralStats = CollateralSnapshot.getData();
                    List<Number> collateralArray = Arrays.asList(
                            (Number) CollateralStats.getOrDefault("s0_Dbrutal", 0),
                            (Number) CollateralStats.getOrDefault("s1_Dletal", 0),
                            (Number) CollateralStats.getOrDefault("s2_Dmistic", 0),
                            (Number) CollateralStats.getOrDefault("s3_armor", 0),
                            (Number) CollateralStats.getOrDefault("s4_resistance", 0),
                            (Number) CollateralStats.getOrDefault("s5_accuracy", 0),
                            (Number) CollateralStats.getOrDefault("s6_evasion", 0),
                            (Number) CollateralStats.getOrDefault("s7_critic", 0),
                            (Number) CollateralStats.getOrDefault("s8_maxHealth", 0)
                    );
                    itemData.put("itemCollateral", collateralArray);
                } else {
                    System.out.println("El documento Bonus no existe.");
                }
                itemsList.add(itemData);
            }
            //System.out.println("items type: " + type);
            return ResponseEntity.ok(itemsList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/getItemsCraftData/{email}")
    public ResponseEntity<List<Map<String, Object>>> getItemsCraftData(
            @PathVariable("email") String email,
            @RequestParam(name = "crafterLevel", required = false) Integer crafterLevel,
            @RequestParam(value = "subtype", required = false) List<String> subtypes) {

       // System.out.println("subtypes: " + subtypes);
        List<Map<String, Object>> itemsList = new ArrayList<>();

        try {
            for (Item item : Item.values()) {
                // Filtrado por lista de subtipos si no es nula
                if (crafterLevel != null && crafterLevel<item.levelToCraft) {
                    continue;
                }
                if (subtypes != null && !subtypes.contains(String.valueOf(item.subtype)) || !"D".equals(item.grade)) {
                    continue;
                }

                Map<String, Object> itemData = new HashMap<>();

                String[] itemsNeeds = {item.needs.item1,item.needs.item2,item.needs.item3};
                int[] itemsAmountsNeeds = {item.needs.amountItem1,item.needs.amountItem2,item.needs.amountItem3};

                itemData.put("ID", item.ID);
                itemData.put("Name", item.name);
                itemData.put("subtype", String.valueOf(item.subtype));
                itemData.put("type", item.type);
                itemData.put("price", item.price);
                itemData.put("expCrafting", item.expCrafting);
                itemData.put("itemNeeds", itemsNeeds);
                itemData.put("itemAmountsNeeds", itemsAmountsNeeds);

                itemData.put("Dbrutal", item.attributes.Dbrutal);
                itemData.put("Dletal", item.attributes.Dletal);
                itemData.put("Dmistic", item.attributes.Dmistic);
                itemData.put("armor", item.attributes.armor);
                itemData.put("resistance", item.attributes.resistance);
                itemData.put("accuracy", item.attributes.accuracy);
                itemData.put("evasion", item.attributes.evasion);
                itemData.put("critic", item.attributes.critic);
                itemData.put("maxHealth", item.attributes.maxHealth);

                itemsList.add(itemData);
            }

            return ResponseEntity.ok(itemsList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getUserData/{email}")
    public ResponseEntity<Map<String, Object>> getUserData(@PathVariable("email") String email) {
        Map<String, Object> response = new HashMap<>();

        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("users").document(email);
            CollectionReference docMaps = db.collection("users").document(email).collection("maps");
            DocumentReference docMap1  = docMaps.document("map1");
            DocumentReference docMap2  = docMaps.document("map2");
            DocumentSnapshot userDoc = docRef.get().get();
            DocumentSnapshot userMap1 = docMap1.get().get();
            DocumentSnapshot userMap2 = docMap2.get().get();
            if (!userDoc.exists()) {
                response.put("error", "El usuario no existe.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            response.put("data", userDoc.getData());
            response.put("dataMap1", userMap1.getData());
            response.put("dataMap2", userMap2.getData());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();  // Agrega esta línea para ver el error en la consola
            response.put("error", "Error al obtener el usuario.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/getCraftersData/{email}")
    public ResponseEntity<List<Map<String, Object>>> getCraftersData(@PathVariable("email") String email) {
        List<Map<String, Object>> craftersData = new ArrayList<>();

        try {
            Firestore db = FirestoreClient.getFirestore();
            CollectionReference userCraftersRef = db.collection("crafters").document(email).collection("usercrafters");
            ApiFuture<QuerySnapshot> future = userCraftersRef.get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                Map<String, Object> data = doc.getData();
                if (data != null) {
                    data.put("ID", doc.getId());
                    craftersData.add(data);
                }
            }

            return ResponseEntity.ok(craftersData);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @PostMapping("/verifyToken")
    public ResponseEntity<Map<String, Object>> verifyToken(@RequestBody Map<String, String> requestBody) {
        Map<String, Object> response = new HashMap<>();
        String idToken = requestBody.get("idToken");

        if (idToken == null || idToken.isEmpty()) {
            response.put("message", "Token is missing.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            // Verifica el token de Firebase
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();

            response.put("message", "Token válido");
            response.put("uid", uid);
            return ResponseEntity.ok(response);

        } catch (FirebaseAuthException e) {
            response.put("message", "Token inválido o credenciales incorrectas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            response.put("message", "Error interno en la verificación del token");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> response = new HashMap<>();
        try {
            String idToken = (String) requestData.get("idToken");

            // Verificar el ID Token con Firebase
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String email = decodedToken.getEmail();
            String uid = decodedToken.getUid();

            // Obtener información del usuario en Firebase
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);

            Firestore db = FirestoreClient.getFirestore();
            DocumentReference userDocRef = db.collection("users").document(email);
            DocumentSnapshot userDoc = userDocRef.get().get();

            // Si el usuario no existe en Firestore, crearlo con valores por defecto
            if (!userDoc.exists()) {
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("email", email);
                userInfo.put("name", "noNamed");
                userInfo.put("heroSize", 0);
                userInfo.put("gold", 100);
                userDocRef.set(userInfo);
                DocumentReference craftersDocRef = db.collection("crafters").document(email);
                DocumentReference weaponsmith   = craftersDocRef.collection("usercrafters").document("1.weaponsmith");
                DocumentReference armorsmith   = craftersDocRef.collection("usercrafters").document("2.armorsmith");
                DocumentReference carpenter   = craftersDocRef.collection("usercrafters").document("3.carpenter");
                DocumentReference tailor   = craftersDocRef.collection("usercrafters").document("4.tailor");
                DocumentReference alchemist   = craftersDocRef.collection("usercrafters").document("5.alchemist");
                DocumentReference map1Doc   = userDocRef.collection("maps").document("map1");
                DocumentReference map2Doc   = userDocRef.collection("maps").document("map2");

                Map<String, Object> weaponsmithData = new HashMap<>();
                weaponsmithData.put("exp", 0);
                weaponsmithData.put("level", 1);
                weaponsmithData.put("maxExp", 1000);
                weaponsmithData.put("type", "WEAPONSMITH");
                weaponsmith.set(weaponsmithData).get();

                Map<String, Object> armorsmithData = new HashMap<>();
                armorsmithData.put("exp", 0);
                armorsmithData.put("level", 1);
                armorsmithData.put("maxExp", 1000);
                armorsmithData.put("type", "ARMORSMITH");
                armorsmith.set(armorsmithData).get();

                Map<String, Object> carpenterData = new HashMap<>();
                carpenterData.put("exp", 0);
                carpenterData.put("level", 1);
                carpenterData.put("maxExp", 1000);
                carpenterData.put("type", "CARPENTER");
                carpenter.set(carpenterData).get();

                Map<String, Object> tailorData = new HashMap<>();
                tailorData.put("exp", 0);
                tailorData.put("level", 1);
                tailorData.put("maxExp", 1000);
                tailorData.put("type", "TAILOR");
                tailor.set(tailorData).get();

                Map<String, Object> alchemistData = new HashMap<>();
                alchemistData.put("exp", 0);
                alchemistData.put("level", 1);
                alchemistData.put("maxExp", 1000);
                alchemistData.put("type", "ALCHEMIST");
                alchemist.set(alchemistData).get();

                Map<String, Object> map1Data = new HashMap<>();
                map1Data.put("M_ENT1", 0);
                map1Data.put("M_FOREST1", 0);
                map1Data.put("M_GOLEM1", 0);
                map1Data.put("M_JABALI1", 0);
                map1Data.put("M_JABALI2", 0);
                map1Data.put("M_JABALI3", 0);
                map1Data.put("M_JABALI4", 0);
                map1Data.put("M_JABALI5", 0);
                map1Data.put("M_JABALI6", 0);
                map1Data.put("M_JABALI7", 0);
                map1Doc.set(map1Data).get();

                Map<String, Object> map2Data = new HashMap<>();
                map2Data.put("M_PEZ2", 0);
                map2Doc.set(map2Data).get();
                System.out.println("✅ Usuario creado en Firestore con email: " + email);
                for (UserInfo userInfo1 : userRecord.getProviderData()) {
                    System.out.println("👉 Proveedor ID: " + userInfo1.getProviderId());
                }
            } else {
                System.out.println("✅ Usuario ya existía en Firestore con email: " + email);
            }

            response.put("message", "Usuario registrado con Email/Password.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("error", "Error en el registro con Email/Password: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/google-login")
    public ResponseEntity<Map<String, Object>> googleLogin(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> response = new HashMap<>();
        try {
            String idToken = (String) requestData.get("idToken");

            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String email = decodedToken.getEmail();

            Firestore db = FirestoreClient.getFirestore();
            DocumentReference userDocRef = db.collection("users").document(email);
            DocumentReference craftersDocRef = db.collection("crafters").document(email);
            DocumentReference weaponsmith   = craftersDocRef.collection("usercrafters").document("1.weaponsmith");
            DocumentReference armorsmith   = craftersDocRef.collection("usercrafters").document("2.armorsmith");
            DocumentReference carpenter   = craftersDocRef.collection("usercrafters").document("3.carpenter");
            DocumentReference tailor   = craftersDocRef.collection("usercrafters").document("4.tailor");
            DocumentReference alchemist   = craftersDocRef.collection("usercrafters").document("5.alchemist");
            DocumentReference map1Doc   = userDocRef.collection("maps").document("map1");
            DocumentReference map2Doc   = userDocRef.collection("maps").document("map2");
            DocumentSnapshot userDoc = userDocRef.get().get();

            if (!userDoc.exists()) {
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("email", email);
                userInfo.put("name", "noNamed");
                userInfo.put("heroSize", 0);
                userInfo.put("gold", 100);
                userDocRef.set(userInfo);

                Map<String, Object> weaponsmithData = new HashMap<>();
                weaponsmithData.put("exp", 0);
                weaponsmithData.put("level", 1);
                weaponsmithData.put("maxExp", 1000);
                weaponsmithData.put("type", "WEAPONSMITH");
                weaponsmith.set(weaponsmithData).get();

                Map<String, Object> armorsmithData = new HashMap<>();
                armorsmithData.put("exp", 0);
                armorsmithData.put("level", 1);
                armorsmithData.put("maxExp", 1000);
                armorsmithData.put("type", "ARMORSMITH");
                armorsmith.set(armorsmithData).get();

                Map<String, Object> carpenterData = new HashMap<>();
                carpenterData.put("exp", 0);
                carpenterData.put("level", 1);
                carpenterData.put("maxExp", 1000);
                carpenterData.put("type", "CARPENTER");
                carpenter.set(carpenterData).get();

                Map<String, Object> tailorData = new HashMap<>();
                tailorData.put("exp", 0);
                tailorData.put("level", 1);
                tailorData.put("maxExp", 1000);
                tailorData.put("type", "TAILOR");
                tailor.set(tailorData).get();

                Map<String, Object> alchemistData = new HashMap<>();
                alchemistData.put("exp", 0);
                alchemistData.put("level", 1);
                alchemistData.put("maxExp", 1000);
                alchemistData.put("type", "ALCHEMIST");
                alchemist.set(alchemistData).get();

                Map<String, Object> map1Data = new HashMap<>();
                map1Data.put("M_ENT1", 0);
                map1Data.put("M_FOREST1", 0);
                map1Data.put("M_GOLEM1", 0);
                map1Data.put("M_JABALI1", 0);
                map1Data.put("M_JABALI2", 0);
                map1Data.put("M_JABALI3", 0);
                map1Data.put("M_JABALI4", 0);
                map1Data.put("M_JABALI5", 0);
                map1Data.put("M_JABALI6", 0);
                map1Data.put("M_JABALI7", 0);
                map1Doc.set(map1Data).get();

                Map<String, Object> map2Data = new HashMap<>();
                map2Data.put("M_PEZ2", 0);
                map2Doc.set(map2Data).get();

                System.out.println("✅ Usuario creado en Firestore.");
            }

            response.put("message", "Inicio de sesión con Google exitoso.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error en el login con Google: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/addHero")
    public ResponseEntity<Map<String, Object>> addHero(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        try {
            Firestore db = FirestoreClient.getFirestore();
            String userEmail = (String) data.get("email");
            String heroID = String.valueOf(data.get("ID"));
            String heroName = (String) data.get("Name");
            CharacterType heroType = CharacterType.valueOf((String) data.get("Type"));

            DocumentReference docRefPrincipal = db.collection("heros").document(userEmail).collection("userheros").document(heroName);
            DocumentReference docRefBaseStats = docRefPrincipal.collection("Details").document("BaseStats");
            DocumentReference docRefEquipment = docRefPrincipal.collection("Details").document("Equipment");
            DocumentReference equipmentStatsRef = docRefPrincipal.collection("Details").document("EquipmentStats");
            DocumentReference docRefuser = db.collection("users").document(userEmail);
            DocumentSnapshot userSnapshot = docRefuser.get().get();
            if (userSnapshot.exists() && userSnapshot.contains("gold")) {
                long currentGold = userSnapshot.getLong("gold");
                long newGold = currentGold - heroType.price;
                docRefuser.update("gold", newGold);
            } else {
                response.put("error", "El campo 'gold' no existe o el usuario no fue encontrado.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Hero newHero = new Hero(heroType, heroName);

            Map<String, Object> principalStats = new HashMap<>();
            principalStats.put("ID", heroID);
            principalStats.put("Name", newHero.name);
            principalStats.put("Type", newHero.type);
            principalStats.put("Level", newHero.level);
            principalStats.put("State", newHero.state);
            principalStats.put("o0_life", newHero.actualLife);
            principalStats.put("o1_exp", newHero.actualExp);
            principalStats.put("s1_skill1", newHero.skill1);
            principalStats.put("s2_skill2", newHero.skill2);
            principalStats.put("s3_skill3", newHero.skill3);
            principalStats.put("s4_skill4", newHero.skill4);
            principalStats.put("s5_skill5", newHero.skill5);
            docRefPrincipal.set(principalStats);

            Map<String, Object> baseStats = new HashMap<>();
            baseStats.put("s0_Dbrutal",newHero.damageBrutal);
            baseStats.put("s1_Dletal",newHero.damageLetal);
            baseStats.put("s2_Dmistic",newHero.damageMistic);
            baseStats.put("s3_armor",newHero.armor);
            baseStats.put("s4_resistance",newHero.resistance);
            baseStats.put("s5_accuracy",newHero.accuracy);
            baseStats.put("s6_evasion",newHero.evasion);
            baseStats.put("s7_critic",newHero.critic);
            baseStats.put("s8_maxHealth",newHero.maxHealth);
            baseStats.put("s9_maxExp",newHero.maxExp);
            docRefBaseStats.set(baseStats);

            Map<String, Object> equipment = new HashMap<>();
            equipment.put("Boots",newHero.Item1);
            equipment.put("Chest",newHero.Item2);
            equipment.put("Gloves",newHero.Item3);
            equipment.put("Helmet",newHero.Item4);
            equipment.put("Jewel",newHero.Item5);
            equipment.put("Weapon",newHero.Item6);
            docRefEquipment.set(equipment);

            List<String> itemKeys = Arrays.asList(
                    newHero.Item1, newHero.Item2, newHero.Item3,
                    newHero.Item4, newHero.Item5, newHero.Item6
            );

            int dBrutal = 0, dLetal = 0, dMistic = 0;
            int armor = 0, resistance = 0, accuracy = 0;
            int evasion = 0, critic = 0, maxHealth = 0;

            for (String itemKey : itemKeys) {
                if (itemKey != null && !itemKey.equals("NULL")) {

                    dBrutal    += Item.valueOf(itemKey).attributes.Dbrutal;
                    dLetal     += Item.valueOf(itemKey).attributes.Dletal;
                    dMistic    += Item.valueOf(itemKey).attributes.Dmistic;
                    armor      += Item.valueOf(itemKey).attributes.armor;
                    resistance += Item.valueOf(itemKey).attributes.resistance;
                    accuracy   += Item.valueOf(itemKey).attributes.accuracy;
                    evasion    += Item.valueOf(itemKey).attributes.evasion;
                    critic     += Item.valueOf(itemKey).attributes.critic;
                    maxHealth  += Item.valueOf(itemKey).attributes.maxHealth;
                }
            }

            Map<String, Object> equipmentStats = new HashMap<>();
            equipmentStats.put("Dbrutal", dBrutal);
            equipmentStats.put("Dletal", dLetal);
            equipmentStats.put("Dmistic", dMistic);
            equipmentStats.put("armor", armor);
            equipmentStats.put("resistance", resistance);
            equipmentStats.put("accuracy", accuracy);
            equipmentStats.put("evasion", evasion);
            equipmentStats.put("critic", critic);
            equipmentStats.put("maxHealth", maxHealth);

            equipmentStatsRef.set(equipmentStats);

            response.put("message", "heroe agregado con éxito");
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            response.put("error", "Error al agregar el heroe ❌: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/equipHero")
    public ResponseEntity<Map<String, Object>> equipHero(@RequestBody Map<String, Object> data) {
        List<ApiFuture<WriteResult>> futures = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            Firestore db = FirestoreClient.getFirestore();
            String userEmail = (String) data.get("email");
            String heroID = (String) data.get("hero");
            String ranure = (String) data.get("ranure");
            String itemOld = String.valueOf(data.get("itemOld"));
            String itemNew = (String) data.get("itemNew");

            DocumentReference EquipmentRef = db.collection("heros").document(userEmail).collection("userheros").document(heroID).collection("Details").document("Equipment");
            DocumentReference EquipmentStatsRef = db.collection("heros").document(userEmail).collection("userheros").document(heroID).collection("Details").document("EquipmentStats");

            Map<String, Object> equipmentData = new HashMap<>();
            equipmentData.put(ranure, itemNew);
            ApiFuture<WriteResult> updateFuture = EquipmentRef.update(equipmentData);
            updateFuture.get(); // esperar a que termine la actualización
            futures.add(updateFuture);

            Map<String, Object> equipmentStatsData = EquipmentRef.get().get().getData();

            int[] totalStatsAported = new int[9];
            String[] slots = {"Boots", "Chest", "Gloves", "Helmet", "Jewel", "Weapon"};
            for (String slot : slots) {
                Object rawValue = equipmentStatsData.get(slot);
                if (rawValue != null) {
                    try {
                        Item item = Item.valueOf(rawValue.toString().trim());
                        totalStatsAported[0] += item.attributes.Dbrutal;
                        totalStatsAported[1] += item.attributes.Dletal;
                        totalStatsAported[2] += item.attributes.Dmistic;
                        totalStatsAported[3] += item.attributes.armor;
                        totalStatsAported[4] += item.attributes.resistance;
                        totalStatsAported[5] += item.attributes.accuracy;
                        totalStatsAported[6] += item.attributes.evasion;
                        totalStatsAported[7] += item.attributes.critic;
                        totalStatsAported[8] += item.attributes.maxHealth;
                    } catch (IllegalArgumentException e) {
                    }
                }
            }

            Map<String, Object> newStats = new HashMap<>();
            newStats.put("Dbrutal", totalStatsAported[0]);
            newStats.put("Dletal", totalStatsAported[1]);
            newStats.put("Dmistic", totalStatsAported[2]);
            newStats.put("armor", totalStatsAported[3]);
            newStats.put("resistance", totalStatsAported[4]);
            newStats.put("accuracy", totalStatsAported[5]);
            newStats.put("evasion", totalStatsAported[6]);
            newStats.put("critic", totalStatsAported[7]);
            newStats.put("maxHealth", totalStatsAported[8]);

            futures.add(EquipmentStatsRef.set(newStats));

            if(itemOld.equalsIgnoreCase(itemNew)){
                response.put("equip", "Heroe ya equipado con ese item en: "+ranure);
            } else{
                removeItemFromInventory(userEmail,itemNew,1);
                if(!itemOld.equalsIgnoreCase("NULL")){
                    response.put("equip", "Heroe actualizado en: "+ranure);
                    String[] reward = {itemOld} ;
                    addRewardsToInventory(userEmail, reward);

                } else{
                    response.put("equip", "Heroe engarzado en: "+ranure);
                }
            }

            for (ApiFuture<WriteResult> future : futures) {
                future.get();
            }

            response.put("message", "heroe equipado con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error al equipar al heroe ❌: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/changeHeroName")
    public ResponseEntity<Map<String, Object>> changeHeroName(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        String userEmail = (String) data.get("email");
        String reference = (String) data.get("reference");

        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("heros").document(userEmail).collection("userheros").document(reference);

            // ✅ Solo actualiza el campo "Name"
            docRef.update("Name", data.get("newName")).get();

            response.put("message", "ID del héroe: " + reference + " actualizado a nombre: " + data.get("newName"));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Error al actualizar el nombre del héroe.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/changeHeroSkill")
    public ResponseEntity<Map<String, Object>> changeHeroSkill(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        String userEmail = (String) data.get("email");
        String reference = (String) data.get("reference");
        String skillCollection = (String) data.get("skillCollection");
        String newSkill = (String) data.get("newSkill");

        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("heros").document(userEmail).collection("userheros").document(reference);

            docRef.update(skillCollection, data.get("newSkill")).get();

            response.put("message", "ID del héroe: " + reference + " "+ skillCollection+" actualizada: " + data.get("newSkill"));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Error al actualizar la skill del héroe.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/battle")
    public ResponseEntity<Map<String, Object>> battle(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();

        try {
            Map<String, Object> heroData = (Map<String, Object>) data.get("hero");

            CharacterType enemyType = CharacterType.valueOf((String) data.get("enemy"));
            String enemyName = (String) data.get("enemy");
            CharacterType heroType = CharacterType.valueOf((String) heroData.get("type"));
            String userEmail = (String) data.get("email");

            Hero heroBattle = new Hero(heroData);
            Enemy enemyBattle = new Enemy(enemyType, enemyName);
            BattleAction battle = new BattleAction(heroBattle, enemyBattle);
            BattleResult battleResult = battle.startBattle();
            String[] rewards = battleResult.getRewardItems();
            Boolean finalBattleResult = battleResult.getFinalResult();

            Integer heroRewardExp = battleResult.getRewardExp();
            Integer heroActualLife = battleResult.getCharacter1().actualLife;
            response.put("message", battleResult.getLog());
            response.put("result", finalBattleResult);
            response.put("reward", rewards);

            String reference = (String) data.get("heroReference");

            if(finalBattleResult){
                addRewardsToInventory(userEmail, rewards);
                updateHeroAfterBattle(userEmail,reference,heroType,heroActualLife,heroRewardExp);
                updateUserMap(userEmail,enemyType);
            }
            else{
                updateHeroAfterBattle(userEmail,reference,heroType,heroActualLife,0);
            }




            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Error durante la batalla.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private void updateHeroAfterBattle(String userEmail, String reference, CharacterType heroType, int heroActualLife, int heroRewardExp) {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("heros").document(userEmail).collection("userheros").document(reference);
        DocumentReference baseStatsRef = docRef.collection("Details").document("BaseStats");

        // Primero obtenemos los dos documentos de forma concurrente
        ApiFuture<DocumentSnapshot> docFuture = docRef.get();
        ApiFuture<DocumentSnapshot> baseStatsFuture = baseStatsRef.get();

        try {
            DocumentSnapshot docSnapshot = docFuture.get();
            DocumentSnapshot baseStatsSnapshot = baseStatsFuture.get();

            if (docSnapshot.exists() && baseStatsSnapshot.exists()) {
                Long currentExp = docSnapshot.getLong("o1_exp");
                Long maxExp = baseStatsSnapshot.getLong("s9_maxExp");

                if (currentExp == null) currentExp = 0L;
                if (maxExp == null) maxExp = 1000L; // por si no existe el campo

                List<Object> newState = Arrays.asList("", "");
                docRef.update("State", newState);
                ApiFuture<WriteResult> stateUpdateFuture = docRef.update("State", newState);
                stateUpdateFuture.get();

                if (currentExp + heroRewardExp >= maxExp) {
                    //System.out.println("LEVEL UP!!!");
                    heroLevelUp(userEmail,reference,heroType);
                } else {
                    ApiFuture<WriteResult> expUpdateFuture = docRef.update("o1_exp", FieldValue.increment(heroRewardExp));
                    ApiFuture<WriteResult> lifeUpdateFuture = docRef.update("o0_life", heroActualLife);

                    expUpdateFuture.get();  // Espera a que Firebase termine
                    lifeUpdateFuture.get(); // Lo mismo
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    private Map<String, Object> updateCrafterAfterCraft(String userEmail, String reference, int crafterRewardExp) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("crafters").document(userEmail).collection("usercrafters").document(reference);

        ApiFuture<DocumentSnapshot> docFuture = docRef.get();
        DocumentSnapshot docSnapshot = docFuture.get();

        Map<String, Object> updatedCrafterData = new HashMap<>();

        if (docSnapshot.exists()) {
            Long currentExp = docSnapshot.getLong("exp");
            Long maxExp = docSnapshot.getLong("maxExp");
            Long currentLevel = docSnapshot.getLong("level");
            String type = docSnapshot.getString("type");

            if (currentExp == null) currentExp = 0L;
            if (maxExp == null) maxExp = 1000L;
            if (currentLevel == null) currentLevel = 0L;

            long newExp = currentExp + crafterRewardExp;

            if (newExp >= maxExp) {
                System.out.println("LEVEL UP!!!");
                long newLevel = currentLevel + 1;
                long newMaxExp = maxExp * 2;

                // Actualizaciones en orden y esperando que terminen
                docRef.update("level", newLevel).get();
                docRef.update("maxExp", newMaxExp).get();
                docRef.update("exp", 0).get();

                updatedCrafterData.put("level", newLevel);
                updatedCrafterData.put("exp", 0L);
                updatedCrafterData.put("maxExp", newMaxExp);
            } else {
                docRef.update("exp", newExp).get();
                updatedCrafterData.put("level", currentLevel);
                updatedCrafterData.put("exp", newExp);
                updatedCrafterData.put("maxExp", maxExp);
            }

            updatedCrafterData.put("type", type);
            updatedCrafterData.put("ID", reference);
        }

        return updatedCrafterData;
    }


    private void heroLevelUp(String userEmail, String reference, CharacterType heroType) {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("heros").document(userEmail).collection("userheros").document(reference);
        DocumentReference baseStatsRef = docRef.collection("Details").document("BaseStats");

        ApiFuture<DocumentSnapshot> docFuture = docRef.get();
        ApiFuture<DocumentSnapshot> baseStatsFuture = baseStatsRef.get();
        try {
            DocumentSnapshot docSnapshot = docFuture.get();
            DocumentSnapshot baseStatsSnapshot = baseStatsFuture.get();

            if (docSnapshot.exists() && baseStatsSnapshot.exists()) {
                System.out.println("LEVEL UP!!!");
                List<ApiFuture<WriteResult>> futures = new ArrayList<>();

                futures.add(baseStatsRef.update("s0_Dbrutal", FieldValue.increment(heroType.statsPerLevel.Dbrutal)));
                futures.add(baseStatsRef.update("s1_Dletal", FieldValue.increment(heroType.statsPerLevel.Dletal)));
                futures.add(baseStatsRef.update("s2_Dmistic", FieldValue.increment(heroType.statsPerLevel.Dmistic)));
                futures.add(baseStatsRef.update("s3_armor", FieldValue.increment(heroType.statsPerLevel.armor)));
                futures.add(baseStatsRef.update("s4_resistance", FieldValue.increment(heroType.statsPerLevel.resistance)));
                futures.add(baseStatsRef.update("s5_accuracy", FieldValue.increment(heroType.statsPerLevel.accuracy)));
                futures.add(baseStatsRef.update("s6_evasion", FieldValue.increment(heroType.statsPerLevel.evasion)));
                futures.add(baseStatsRef.update("s7_critic", FieldValue.increment(heroType.statsPerLevel.critic)));
                futures.add(baseStatsRef.update("s8_maxHealth", FieldValue.increment(heroType.statsPerLevel.maxHealth)));

                Long currentMaxExp = baseStatsSnapshot.getLong("s9_maxExp");
                if (currentMaxExp == null) currentMaxExp = 1000L;
                long newMaxExp = currentMaxExp * heroType.statsPerLevel.maxExp;
                futures.add(baseStatsRef.update("s9_maxExp", newMaxExp));

                futures.add(docRef.update("Level", FieldValue.increment(1)));
                futures.add(docRef.update("o1_exp", 0));
                futures.add(docRef.update("o0_life", baseStatsSnapshot.getLong("s8_maxHealth") + heroType.statsPerLevel.maxHealth));

                // Esperar a que se completen todas las actualizaciones
                for (ApiFuture<WriteResult> future : futures) {
                    future.get();
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
    private void updateUserMap(String userEmail, CharacterType enemy) {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collecRef = db.collection("users").document(userEmail).collection("maps");
        DocumentReference map1Ref = collecRef.document("map1");
        DocumentReference map2Ref = collecRef.document("map2");
        //System.out.println("ENEMY MISSION: " + enemy);

        try {
            Map<String, Object> updates = new HashMap<>();
            DocumentReference map = map1Ref;
            switch (enemy) {
                case FOREST1: updates.put("M_FOREST1", FieldValue.increment(1));map = map1Ref; break;
                case FOREST1_UP1: updates.put("M_FOREST1", FieldValue.increment(1));map = map1Ref; break;
                case FOREST1_UP2: updates.put("M_FOREST1", FieldValue.increment(1));map = map1Ref; break;
                case GOLEM1: updates.put("M_GOLEM1", FieldValue.increment(1));map = map1Ref; break;
                case GOLEM1_UP1: updates.put("M_GOLEM1", FieldValue.increment(1));map = map1Ref; break;
                case GOLEM1_UP2: updates.put("M_GOLEM1", FieldValue.increment(1));map = map1Ref; break;
                case ENT1: updates.put("M_ENT1", FieldValue.increment(1));map = map1Ref; break;
                case ENT1_UP1: updates.put("M_ENT1", FieldValue.increment(1));map = map1Ref; break;
                case ENT1_UP2: updates.put("M_ENT1", FieldValue.increment(1));map = map1Ref; break;
                case JABALI1: updates.put("M_JABALI1", FieldValue.increment(1));map = map1Ref; break;
                case JABALI1_UP1: updates.put("M_JABALI1", FieldValue.increment(1));map = map1Ref; break;
                case JABALI1_UP2: updates.put("M_JABALI1", FieldValue.increment(1));map = map1Ref; break;
                case JABALI2: updates.put("M_JABALI2", FieldValue.increment(1));map = map1Ref; break;
                case JABALI2_UP1: updates.put("M_JABALI2", FieldValue.increment(1));map = map1Ref; break;
                case JABALI2_UP2: updates.put("M_JABALI2", FieldValue.increment(1));map = map1Ref; break;
                case JABALI3: updates.put("M_JABALI3", FieldValue.increment(1));map = map1Ref; break;
                case JABALI3_UP1: updates.put("M_JABALI3", FieldValue.increment(1));map = map1Ref; break;
                case JABALI3_UP2: updates.put("M_JABALI3", FieldValue.increment(1));map = map1Ref; break;
                case JABALI4: updates.put("M_JABALI4", FieldValue.increment(1));map = map1Ref; break;
                case JABALI4_UP1: updates.put("M_JABALI4", FieldValue.increment(1));map = map1Ref; break;
                case JABALI4_UP2: updates.put("M_JABALI4", FieldValue.increment(1));map = map1Ref; break;
                case JABALI5: updates.put("M_JABALI5", FieldValue.increment(1));map = map1Ref; break;
                case JABALI5_UP1: updates.put("M_JABALI5", FieldValue.increment(1));map = map1Ref; break;
                case JABALI5_UP2: updates.put("M_JABALI5", FieldValue.increment(1));map = map1Ref; break;
                case JABALI6: updates.put("M_JABALI6", FieldValue.increment(1));map = map1Ref;; break;
                case JABALI6_UP1: updates.put("M_JABALI6", FieldValue.increment(1));map = map1Ref; break;
                case JABALI6_UP2: updates.put("M_JABALI6", FieldValue.increment(1));map = map1Ref; break;
                case JABALI7: updates.put("M_JABALI7", FieldValue.increment(1));map = map1Ref; break;
                case JABALI7_UP1: updates.put("M_JABALI7", FieldValue.increment(1));map = map1Ref; break;
                case JABALI7_UP2: updates.put("M_JABALI7", FieldValue.increment(1));map = map1Ref; break;

                case PEZ2: updates.put("M_PEZ2", FieldValue.increment(1));map = map2Ref; break;
                case PEZ2_UP1: updates.put("M_PEZ2", FieldValue.increment(1));map = map2Ref; break;
            }

            if (!updates.isEmpty()) {
                    ApiFuture<WriteResult> future = map.set(updates, SetOptions.merge());
                    future.get();
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/usePotion")
    public ResponseEntity<Map<String, Object>> usePotion(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        String userEmail = (String) data.get("email");
        String itemID = String.valueOf(data.get("itemID"));
        String reference = (String) data.get("heroReference");
        System.out.println("reference "+reference);
        Item item = null;
        try {
            item = Item.valueOf(itemID);
        } catch (IllegalArgumentException e) {
            response.put("error", "ItemID no válido: " + itemID);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            removeItemFromInventory(userEmail, itemID, 1);
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("heros").document(userEmail).collection("userheros").document(reference);
            DocumentReference baseStatsRef = docRef.collection("Details").document("BaseStats");
            ApiFuture<DocumentSnapshot> docFuture = docRef.get();
            ApiFuture<DocumentSnapshot> baseStatsFuture = baseStatsRef.get();
            DocumentSnapshot docSnapshot = docFuture.get();
            DocumentSnapshot baseStatsSnapshot = baseStatsFuture.get();
            if (docSnapshot.exists()) {

                List<ApiFuture<WriteResult>> futures = new ArrayList<>();

                Long currentMaxLife = baseStatsSnapshot.getLong("s8_maxHealth");
                Long currentActualLife = docSnapshot.getLong("o0_life");
                if (currentMaxLife == null) currentMaxLife = 10L;
                if (currentActualLife == null) currentActualLife = 10L;
                long newLife = item.attributes.maxHealth + currentActualLife ;if(newLife>currentMaxLife){newLife=currentMaxLife;}
                System.out.println("vida actual "+currentActualLife);
                System.out.println("nueva vida "+newLife);
                futures.add(docRef.update("o0_life", newLife));

                // Esperar a que se completen todas las actualizaciones
                for (ApiFuture<WriteResult> future : futures) {
                    future.get();
                }
            }
        response.put("message", "pocion eliminada correctamente ✅"+userEmail+" "+itemID+" "+1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("ERROR FATAL");
            System.out.println("AL INTENTAR USAR POCION " + userEmail + " " + itemID + " " + 1);
            e.printStackTrace(); // ← NECESARIO PARA SABER QUÉ FALLA
            response.put("error", "Error al usar pocion ❌: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @PostMapping("/sellItem")
    public ResponseEntity<Map<String, Object>> sellItem(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        Firestore db = FirestoreClient.getFirestore();
        String userEmail = (String) data.get("email");
        String itemID = String.valueOf(data.get("itemID"));
        int amountToRemove = ((Number) data.get("amountSell")).intValue();
        try {
             // <-- Aquí el cambio
            removeItemFromInventory(userEmail, itemID, amountToRemove);;
            DocumentReference docRefuser = db.collection("users").document(userEmail);;
            DocumentSnapshot userSnapshot = docRefuser.get().get();

            if (userSnapshot.exists() && userSnapshot.contains("gold")) {
                long currentGold = userSnapshot.getLong("gold");

                long newGold = currentGold + (long) Item.valueOf(itemID).price *amountToRemove;
                docRefuser.update("gold", newGold);
            } else {
                response.put("error", "El campo 'gold' no existe o el usuario no fue encontrado.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            response.put("message", "Items vendidos correctamente ✅"+userEmail+" "+itemID+" "+amountToRemove);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("ERROR FATAL");
            System.out.println("AL INTENTAR VENDER "+userEmail+" "+itemID+" "+amountToRemove);
            response.put("error", "Error al vender los items ❌: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/craftItem")
    public ResponseEntity<Map<String, Object>> craftItem(@RequestBody Map<String, Object> data) {
        List<ApiFuture<WriteResult>> futures = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            String userEmail = (String) data.get("email");
            String crafterID = (String) data.get("crafterID");
            String itemID = (String) data.get("itemID");
            int itemAmount = (Integer) data.get("itemAmount");
            Item item = Item.valueOf(itemID);

            int item1GradeRef = Integer.parseInt(data.get("item1GradeRef").toString());
            int item2GradeRef = Integer.parseInt(data.get("item2GradeRef").toString());
            int item3GradeRef = Integer.parseInt(data.get("item3GradeRef").toString());
            String[] rewards = new String[itemAmount];
            String ItemToCraft;

            for (int i = 0; i < itemAmount; i++) {
                ItemToCraft = reprobCraft(item, itemID.substring(0, itemID.length() - 1),
                        item.needs.amountItem1 + item.needs.amountItem2 + item.needs.amountItem3,
                        item1GradeRef, item2GradeRef, item3GradeRef);
                rewards[i] = ItemToCraft;
            }

            removeItemFromInventory(userEmail, item.needs.item1.substring(0, item.needs.item1.length() - 1) + item1GradeRef, item.needs.amountItem1 * itemAmount);
            removeItemFromInventory(userEmail, item.needs.item2.substring(0, item.needs.item2.length() - 1) + item2GradeRef, item.needs.amountItem2 * itemAmount);
            removeItemFromInventory(userEmail, item.needs.item3.substring(0, item.needs.item3.length() - 1) + item3GradeRef, item.needs.amountItem3 * itemAmount);
            addRewardsToInventory(userEmail, rewards);

            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRefuser = db.collection("users").document(userEmail);
            DocumentSnapshot userSnapshot = docRefuser.get().get();

            if (userSnapshot.exists() && userSnapshot.contains("gold")) {
                long currentGold = userSnapshot.getLong("gold");
                long newGold = currentGold - ((item.price / 10) * itemAmount);

                ApiFuture<WriteResult> updateFuture = docRefuser.update("gold", newGold);
                futures.add(updateFuture);
            } else {
                response.put("error", "El campo 'gold' no existe o el usuario no fue encontrado.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Esperar todas las actualizaciones previas (incluida la del oro)
            for (ApiFuture<WriteResult> future : futures) {
                future.get();
            }

            // Calcular EXP y actualizar crafter
            int crafterRewardExp = 0;
            for (String reward : rewards) {
                crafterRewardExp += Item.valueOf(reward).expCrafting;
            }

            Map<String, Object> updatedCrafter = updateCrafterAfterCraft(userEmail, crafterID, crafterRewardExp);

            // Respuesta final
            response.put("message", "item crafteado con éxito");
            response.put("itemsCrafted", rewards);
            response.put("crafterUpdated", updatedCrafter);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Error al craftear el item ❌: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    public static String reprobCraft(Item item, String startID, Integer totalAmount, Integer item1GradeRef,Integer item2GradeRef,Integer item3GradeRef) {
        double prob = (double) 1 /totalAmount;
        //PARA QUE SOLO EXISTA POSIBILIDAD ENTRE LOS GRADE INTRODUCIDOS
        /*double Dresult = 0;double Cresult = 0;double Bresult = 0;double Aresult = 0;double Sresult = 0;

        if(item1GradeRef==1){Dresult= Dresult+prob*item.needs.amountItem1;}
        if(item1GradeRef==2){Cresult= Cresult+prob*item.needs.amountItem1;}
        if(item1GradeRef==3){Bresult= Bresult+prob*item.needs.amountItem1;}
        if(item1GradeRef==4){Aresult= Aresult+prob*item.needs.amountItem1;}
        if(item1GradeRef==5){Sresult= Sresult+prob*item.needs.amountItem1;}

        if(item2GradeRef==1){Dresult= Dresult+prob*item.needs.amountItem2;}
        if(item2GradeRef==2){Cresult= Cresult+prob*item.needs.amountItem2;}
        if(item2GradeRef==3){Bresult= Bresult+prob*item.needs.amountItem2;}
        if(item2GradeRef==4){Aresult= Aresult+prob*item.needs.amountItem2;}
        if(item2GradeRef==5){Sresult= Sresult+prob*item.needs.amountItem2;}

        if(item3GradeRef==1){Dresult= Dresult+prob*item.needs.amountItem3;}
        if(item3GradeRef==2){Cresult= Cresult+prob*item.needs.amountItem3;}
        if(item3GradeRef==3){Bresult= Bresult+prob*item.needs.amountItem3;}
        if(item3GradeRef==4){Aresult= Aresult+prob*item.needs.amountItem3;}
        if(item3GradeRef==5){Sresult= Sresult+prob*item.needs.amountItem3;}

        double random = Math.random();
        if (random < Dresult) return startID+1;//D
        if (random < Dresult + Cresult) return startID+2;//C
        if (random < Dresult + Cresult + Bresult) return startID+3;//B
        if (random < Dresult + Cresult + Bresult + Aresult) return startID+4;//A
        return startID+5;//S*/

        //PARA QUE EXISTA POSIBILIDAD HIBRIDA CHUNGO QUE TE CAGAS, NO EXPLICAR, SOLO YO SABER.
        double[] calidadBruta = new double[5]; // D(0), C(1), B(2), A(3), S(4)
        int min = 4, max = 0;

        // Extraer cantidades de los paquetes
        int[] cantidades = new int[] {
                item.needs.amountItem1,
                item.needs.amountItem2,
                item.needs.amountItem3
        };

        int[] grades = new int[] { item1GradeRef, item2GradeRef, item3GradeRef };

        for (int i = 0; i < 3; i++) {
            int g = grades[i] - 1; // Ajustar índice (1-5 a 0-4)
            int cant = cantidades[i];
            calidadBruta[g] += cant;
            min = Math.min(min, g);
            max = Math.max(max, g);
        }

        // Distribuir pesos suavemente entre min y max
        double[] probabilidades = new double[5];
        double suma = 0;

        for (int i = min; i <= max; i++) {
            for (int j = min; j <= max; j++) {
                double dist = Math.abs(i - j);
                double peso = calidadBruta[j] / (1 + dist);
                probabilidades[i] += peso;
            }
            suma += probabilidades[i];
        }

        // Normalizar
        for (int i = min; i <= max; i++) {
            probabilidades[i] /= suma;
        }

        // Sorteo
        double r = Math.random();
        double acumulado = 0;
        for (int i = min; i <= max; i++) {
            acumulado += probabilidades[i];
            if (r < acumulado) {

                return startID + (i + 1);
            }
        }

        return startID + (max + 1);


    }
    @GetMapping("/getEnemyData/{enemyType}")
    public ResponseEntity<Map<String, Object>> getEnemyData(@PathVariable("enemyType") String enemyType) {

        Map<String, Object> response = new HashMap<>();
        Enemy enemy = new Enemy(CharacterType.valueOf(enemyType), enemyType);
        response.put("enemy", enemy);

        MissionReward reward = MissionReward.valueOf(String.valueOf(enemy.type));
        String[] loot1 = {
                reward.Item1.posibleItem1_1,
                reward.Item1.posibleItem1_2,
                reward.Item1.posibleItem1_3
        };
        String[] loot2 = {
                reward.Item2.posibleItem2_1,
                reward.Item2.posibleItem2_2,
                reward.Item2.posibleItem2_3
        };
        String[] loot3 = {
                reward.Item3.posibleItem3_1,
                reward.Item3.posibleItem3_2,
                reward.Item3.posibleItem3_3
        };

        double[] probabilities = {0.5, 0.3, 0.2};

        Map<String, List<Double>> itemToChances = new HashMap<>();

        processSlot(loot1, probabilities, itemToChances);
        processSlot(loot2, probabilities, itemToChances);
        processSlot(loot3, probabilities, itemToChances);

        Map<String, Integer> finalProbabilities = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : itemToChances.entrySet()) {
            String item = entry.getKey();
            List<Double> chances = entry.getValue();

            double probNone = 1.0;
            for (double chance : chances) {
                probNone *= (1.0 - chance);
            }

            double probAtLeastOnce = 1.0 - probNone;
            finalProbabilities.put(item, (int) Math.round(probAtLeastOnce * 100));
        }

        // Usar AtomicInteger para mantener índice mutable en lambda
        AtomicInteger index = new AtomicInteger(1);
        finalProbabilities.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .forEachOrdered(entry -> {
                    int i = index.getAndIncrement();
                    response.put("Item" + i, entry.getKey());
                    response.put("probItem" + i, entry.getValue() + "%");
                });

        return ResponseEntity.ok(response);
    }

    private static void processSlot(String[] loot, double[] probabilities, Map<String, List<Double>> itemToChances) {
        Map<String, Double> itemProbInSlot = new HashMap<>();

        for (int i = 0; i < loot.length; i++) {
            String item = loot[i];
            double prob = probabilities[i];
            itemProbInSlot.put(item, itemProbInSlot.getOrDefault(item, 0.0) + prob);
        }

        for (Map.Entry<String, Double> entry : itemProbInSlot.entrySet()) {
            itemToChances.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).add(entry.getValue());
        }
    }


    @PostMapping("/updateHeroState")
    public ResponseEntity<Map<String, Object>> updateHeroState(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        String userEmail = (String) data.get("email");
        String reference = (String) data.get("heroReference");
        String enemyString = (String) data.get("enemy");
        Boolean admin = (Boolean) data.get("admin");
        Enemy enemy = new Enemy(CharacterType.valueOf(enemyString), enemyString);

        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("heros").document(userEmail).collection("userheros").document(reference);

            List<Object> newState = new ArrayList<>();
            newState.add(enemyString);

            Calendar calendar = Calendar.getInstance();
            if(admin){calendar.add(Calendar.SECOND,5 );}
            //else{calendar.add(Calendar.MINUTE, 2);} // suma 2 minutos a la hora actual
            else{
                calendar.add(Calendar.SECOND, enemy.level*30);
            }
            Date futureDate = calendar.getTime();

            newState.add(futureDate);

            docRef.update("State", newState);

            response.put("message", "ID del héroe: " + reference + " actualizado a nombre: " + data.get("newName"));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Error al actualizar el nombre del héroe.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



















    private void addRewardsToInventory(String userEmail, String[] rewards) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        Map<String, Integer> rewardCountMap = new HashMap<>();
        List<ApiFuture<WriteResult>> futures = new ArrayList<>();

        // Contar cuántos hay de cada rewardId
        for (String rewardId : rewards) {
            rewardCountMap.put(rewardId, rewardCountMap.getOrDefault(rewardId, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : rewardCountMap.entrySet()) {
            String rewardId = entry.getKey();
            int totalToAdd = entry.getValue();

            CollectionReference inventoryRef = db.collection("storage").document(userEmail).collection("inventory");

            // Buscar todos los documentos con ese ID de item
            List<QueryDocumentSnapshot> matchingItems = inventoryRef.whereEqualTo("ID", rewardId).get().get().getDocuments();

            for (QueryDocumentSnapshot doc : matchingItems) {
                Long currentAmount = doc.getLong("amount");
                if (currentAmount == null || currentAmount >= 20) continue;

                long spaceLeft = 20 - currentAmount;
                long addNow = Math.min(spaceLeft, totalToAdd);

                ApiFuture<WriteResult> futureUpdate = doc.getReference().update("amount", currentAmount + addNow);
                futures.add(futureUpdate);
                totalToAdd -= addNow;

                if (totalToAdd == 0) break;
            }

            // Calcular sufijo numérico máximo actual
            int maxSuffix = 0;
            for (QueryDocumentSnapshot doc : matchingItems) {
                String docId = doc.getId();
                if (docId.equals(rewardId)) {
                    maxSuffix = Math.max(maxSuffix, 0);
                } else if (docId.startsWith(rewardId + "_")) {
                    String suffix = docId.substring((rewardId + "_").length());
                    try {
                        int num = Integer.parseInt(suffix);
                        maxSuffix = Math.max(maxSuffix, num);
                    } catch (NumberFormatException ignored) {}
                }
            }
           // Crear nuevos documentos si aún queda por añadir
            while (totalToAdd > 0) {
                long amountForNew = Math.min(20, totalToAdd);
                String paddedSuffix = String.format("%03d", ++maxSuffix); // Padding con ceros
                String newDocId = rewardId + "_" + paddedSuffix;
                DocumentReference BaseStatsRef = inventoryRef.document(newDocId).collection("Attributes").document("BaseStats");
                DocumentReference BonusRef = inventoryRef.document(newDocId).collection("Attributes").document("Bonus");
                DocumentReference CollateralRef = inventoryRef.document(newDocId).collection("Attributes").document("Collateral");

                Map<String, Object> newItem = new HashMap<>();
                newItem.put("ID", String.valueOf(Item.valueOf(rewardId)));
                newItem.put("amount", amountForNew);
                newItem.put("grade", String.valueOf(Item.valueOf(rewardId).grade));
                newItem.put("name", String.valueOf(Item.valueOf(rewardId).name));
                newItem.put("price", String.valueOf(Item.valueOf(rewardId).price));
                newItem.put("subtype", String.valueOf(Item.valueOf(rewardId).subtype));
                newItem.put("type", String.valueOf(Item.valueOf(rewardId).type));
                Map<String, Object> newItemBaseStats = new HashMap<>();
                newItemBaseStats.put("s0_Dbrutal", Long.valueOf(Item.valueOf(rewardId).attributes.Dbrutal));
                newItemBaseStats.put("s1_Dletal", Long.valueOf(Item.valueOf(rewardId).attributes.Dletal));
                newItemBaseStats.put("s2_Dmistic", Long.valueOf(Item.valueOf(rewardId).attributes.Dmistic));
                newItemBaseStats.put("s3_armor", Long.valueOf(Item.valueOf(rewardId).attributes.armor));
                newItemBaseStats.put("s4_resistance", Long.valueOf(Item.valueOf(rewardId).attributes.resistance));
                newItemBaseStats.put("s5_accuracy", Long.valueOf(Item.valueOf(rewardId).attributes.accuracy));
                newItemBaseStats.put("s6_evasion", Long.valueOf(Item.valueOf(rewardId).attributes.evasion));
                newItemBaseStats.put("s7_critic", Long.valueOf(Item.valueOf(rewardId).attributes.critic));
                newItemBaseStats.put("s8_maxHealth", Long.valueOf(Item.valueOf(rewardId).attributes.maxHealth));
                Map<String, Object> newItemBonus = new HashMap<>();
                newItemBonus.put("s0_Dbrutal", Long.valueOf(Item.valueOf(rewardId).subtype.benefits.Dbrutal));
                newItemBonus.put("s1_Dletal", Long.valueOf(Item.valueOf(rewardId).subtype.benefits.Dletal));
                newItemBonus.put("s2_Dmistic", Long.valueOf(Item.valueOf(rewardId).subtype.benefits.Dmistic));
                newItemBonus.put("s3_armor", Long.valueOf(Item.valueOf(rewardId).subtype.benefits.armor));
                newItemBonus.put("s4_resistance", Long.valueOf(Item.valueOf(rewardId).subtype.benefits.resistance));
                newItemBonus.put("s5_accuracy", Long.valueOf(Item.valueOf(rewardId).subtype.benefits.accuracy));
                newItemBonus.put("s6_evasion", Long.valueOf(Item.valueOf(rewardId).subtype.benefits.evasion));
                newItemBonus.put("s7_critic", Long.valueOf(Item.valueOf(rewardId).subtype.benefits.critic));
                newItemBonus.put("s8_maxHealth", Long.valueOf(Item.valueOf(rewardId).subtype.benefits.maxHealth));
                Map<String, Object> newItemCollateral = new HashMap<>();
                newItemCollateral.put("s0_Dbrutal", Long.valueOf(Item.valueOf(rewardId).subtype.collateral.Dbrutal));
                newItemCollateral.put("s1_Dletal", Long.valueOf(Item.valueOf(rewardId).subtype.collateral.Dletal));
                newItemCollateral.put("s2_Dmistic", Long.valueOf(Item.valueOf(rewardId).subtype.collateral.Dmistic));
                newItemCollateral.put("s3_armor", Long.valueOf(Item.valueOf(rewardId).subtype.collateral.armor));
                newItemCollateral.put("s4_resistance", Long.valueOf(Item.valueOf(rewardId).subtype.collateral.resistance));
                newItemCollateral.put("s5_accuracy", Long.valueOf(Item.valueOf(rewardId).subtype.collateral.accuracy));
                newItemCollateral.put("s6_evasion", Long.valueOf(Item.valueOf(rewardId).subtype.collateral.evasion));
                newItemCollateral.put("s7_critic", Long.valueOf(Item.valueOf(rewardId).subtype.collateral.critic));
                newItemCollateral.put("s8_maxHealth", Long.valueOf(Item.valueOf(rewardId).subtype.collateral.maxHealth));

                if (!matchingItems.isEmpty()) {
                    Map<String, Object> originalData = matchingItems.get(0).getData();
                    for (String key : originalData.keySet()) {
                        if (!key.equals("amount") && !key.equals("ID")) {
                            newItem.put(key, originalData.get(key));
                        }
                    }
                }

                ApiFuture<WriteResult> futureSet = inventoryRef.document(newDocId).set(newItem);
                ApiFuture<WriteResult> futureSet2 = BaseStatsRef.set(newItemBaseStats);
                ApiFuture<WriteResult> futureSet3 = BonusRef.set(newItemBonus);
                ApiFuture<WriteResult> futureSet4 = CollateralRef.set(newItemCollateral);
                futures.add(futureSet);
                futures.add(futureSet2);
                futures.add(futureSet3);
                futures.add(futureSet4);
                totalToAdd -= amountForNew;
            }
        }

        // Esperar a que todas las escrituras se completen
        for (ApiFuture<WriteResult> future : futures) {
            future.get(); // Bloquea hasta que finalice
        }
    }

    private void removeItemFromInventory(String userEmail, String itemId, int amountToRemove) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference inventoryRef = db.collection("storage").document(userEmail).collection("inventory");

        List<QueryDocumentSnapshot> matchingItems = inventoryRef
                .whereEqualTo("ID", itemId)
                .get()
                .get()
                .getDocuments();

        Long currentAmount = (Long) matchingItems.get(matchingItems.size() - 1).get("amount");

        long toRemove = Math.min(amountToRemove, currentAmount);
        long diference = currentAmount - toRemove;

        // Actualiza o elimina el stack más pequeño
        DocumentReference targetDocRef = matchingItems.get(matchingItems.size() - 1).getReference();

        if (diference <= 0) {
            // Primero elimina las subcolecciones
            DocumentReference baseStatsRef = targetDocRef.collection("Attributes").document("BaseStats");
            DocumentReference bonusRef = targetDocRef.collection("Attributes").document("Bonus");
            DocumentReference collateralRef = targetDocRef.collection("Attributes").document("Collateral");
            ApiFuture<WriteResult> deleteBaseStats = baseStatsRef.delete();
            ApiFuture<WriteResult> deleteBonus = bonusRef.delete();
            ApiFuture<WriteResult> deleteCollateral = collateralRef.delete();
            // espera a que se complete
            deleteBaseStats.get();
            deleteBonus.get();
            deleteCollateral.get();

            // Luego elimina el documento padre
            ApiFuture<WriteResult> deleteItem = targetDocRef.delete();
            deleteItem.get();
        } else {
            // Solo hay que actualizar la cantidad
            ApiFuture<WriteResult> updateAmount = targetDocRef.update("amount", diference);
            updateAmount.get();
        }

        // Si hay más ítems que eliminar, seguimos con el anterior stack
        if (amountToRemove > toRemove && matchingItems.size() > 1) {
            DocumentReference previousDocRef = matchingItems.get(matchingItems.size() - 2).getReference();
            currentAmount = (Long) matchingItems.get(matchingItems.size() - 2).get("amount");
            long remanente = amountToRemove - toRemove;
            long finalRemanente = currentAmount - remanente;

            System.out.println("se van a eliminar del doc anterior: " + remanente);
            ApiFuture<WriteResult> updatePrevious = previousDocRef.update("amount", finalRemanente);
            updatePrevious.get();
        }
    }

    @GetMapping("/gettext1")
    public ResponseEntity<Map<String, Object>> gettext1() {
        Map<String, Object> response = new HashMap<>();

        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("textos").document("texto1");
            DocumentSnapshot userDoc = docRef.get().get();

            if (!userDoc.exists()) {
                response.put("error", "El doc no existe.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            response.put("texto", userDoc.getData());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Error al obtener el doc.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/enterAdmin")
    public ResponseEntity<Boolean> enterAdmin(@RequestBody AdminRequest request) {
        try {
            Firestore db = FirestoreClient.getFirestore();

            // Obtenemos todos los documentos de la colección "credentials"
            ApiFuture<QuerySnapshot> future = db.collection("credentials").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            // Recorremos y verificamos
            for (QueryDocumentSnapshot doc : documents) {
                String dbUser = doc.getString("user");
                String dbPass = doc.getString("pass");

                if (dbUser != null && dbPass != null &&
                        dbUser.equals(request.getUser()) &&
                        dbPass.equals(request.getPass())) {
                    System.out.println("credenciales correctas");
                    return ResponseEntity.ok(true); // credenciales correctas
                }
            }
            System.out.println("credenciales incorrectas");
            return ResponseEntity.ok(false); // si no coincide ninguno

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(false);
        }
    }

    // Clase para mapear el JSON recibido
    public static class AdminRequest {
        private String user;
        private String pass;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }
    }

}