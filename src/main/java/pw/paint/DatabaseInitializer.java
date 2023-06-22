package pw.paint;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pw.paint.model.*;
import pw.paint.repository.RecipeRepository;
import pw.paint.repository.TagRepository;
import pw.paint.repository.UserRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final TagRepository tagRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        addTags();
        addUsers();

        // Przepisy Ani
        User user = userRepository.findByUsername("Ania").get();
        Optional<Recipe> checkRecipe = recipeRepository.findByName("Szybka zapiekanka z mięsem mielonym i ziemniakami");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Szybka zapiekanka z mięsem mielonym i ziemniakami", user, true, 60);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("mielona wołowina 500 g");
            ingredients.add("cebula 1 sztuka");
            ingredients.add("ziemniaki 2 kg");
            ingredients.add("ser żółty 220 g");
            ingredients.add("ser cheddar 100 g");
            ingredients.add("bulion 100 ml");
            ingredients.add("jajko 1 sztuka");
            ingredients.add("natka pietruszki 4 gałązki");
            ingredients.add("sól 1/2 łyżeczki");
            ingredients.add("pieprz 1/4 łyżeczki");
            ingredients.add("oliwa 3 łyżki");
            recipe.setIngredients(ingredients);

            String imagePath = "src/main/resources/static/init_jpg/kotlet.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<String> steps = new ArrayList<>();
            steps.add("Cebulę obierz i drobno posiekaj.");
            steps.add("Na patelni rozgrzej oliwę. Dodaj wołowinę i cebulę. Smaż wszystko przez kilka minut do zrumienienia. Dopraw solą i pieprzem. W razie potrzeby odcedź tłuszcz.");
            steps.add("Duże naczynie żaroodporne nasmaruj oliwą. Na dnie połóż ok. 1/5 ziemniaków, a na to ok. ¼ wołowiny. Posyp odrobiną tartego sera żółtego. Powtarzaj te warstwy do wykończenia składników.");
            steps.add("W garnuszku wymieszaj bulion z mlekiem. Dodaj tarty ser cheddar i podgrzewaj wszystko, stale mieszając, do rozpuszczenia sera. Zdejmij z ognia i ostudź. Dodaj jajko i wymieszaj. Mieszanką zalej zapiekankę.");
            steps.add("Na wierzchu poukładaj odłożone wcześniej ziemniaki, by lekko na siebie zachodziły. Posyp siekaną natką pietruszki.");
            steps.add("Piekarnik nagrzej do 180 st. Celsjusza. Naczynie przykryj folią aluminiową. Szybką zapiekankę z mięsem mielonym i ziemniakami piecz przez 60 minut, do miękkości ziemniaków. Usuń folię i kontynuuj pieczenie przez kolejne 20 minut, do zrumienienia.");
            recipe.setSteps(steps);

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("obiad").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);
        }

        checkRecipe = recipeRepository.findByName("Zapiekanka ziemniaczana");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Zapiekanka ziemniaczana", user, true, 60);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("Ziemniaki  1 kg");
            ingredients.add("Cebula 1 duża sztuka");
            ingredients.add("Ser żółty 200 g");
            ingredients.add("Śmietana kwaśna  200 ml");
            ingredients.add("Jajka  2 sztuki");
            ingredients.add("Sól szczypta");
            ingredients.add("Pieprz szczypta");
            recipe.setIngredients(ingredients);

            String imagePath = "src/main/resources/static/init_jpg/recipe2.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<String> steps = new ArrayList<>();
            steps.add("Ziemniaki należy obrać i umyć. Następnie zetrzeć je na tarce o drobnych oczkach lub pokroić na bardzo cienkie plasterki.");
            steps.add("Cebulę drobno posiekać.");
            steps.add("W rondlu rozgrzać niewielką ilość masła i podsmażyć cebulę, aż będzie miękka i lekko zrumieniona.");
            steps.add("W misce wymieszać ziemniaki z podsmażoną cebulą. Dodaj ser żółty, śmietanę kwaśną oraz jajka. Całość dokładnie wymieszać.");
            steps.add("Doprawić masę solą i pieprzem według własnego gustu. Warto pamiętać, że ziemniaki mogą wymagać nieco więcej soli, aby smak był wyraźny.");
            steps.add("Następnie, formę do zapiekania należy natłuścić masłem. Jeśli nie masz specjalnej formy do zapiekania, możesz użyć zwykłej naczyniowej lub żaroodpornej.");
            steps.add("Przełożyć masę ziemniaczaną do natłuszczonej formy, równomiernie rozprowadzając.");
            steps.add("Wstawić formę do nagrzanego piekarnika (180°C) i piec zapiekankę przez około 45-50 minut lub do momentu, gdy ziemniaki będą miękkie, a wierzch lekko zrumieniony.");
            steps.add("Po upieczeniu, wyjąć zapiekankę z piekarnika i dać jej chwilę ostygnąć, aby ściągnęła się i nabierała konsystencji.");
            recipe.setSteps(steps);

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("polskie").get();
            tags.add(tag);
            tag = tagRepository.findByName("wegetariańskie").get();
            tags.add(tag);
            tag = tagRepository.findByName("łagodne").get();
            tags.add(tag);
            tag = tagRepository.findByName("obiad").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);

        }

        checkRecipe = recipeRepository.findByName("Tradycyjny piernik");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Tradycyjny piernik", user, true, 50);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("Mąka pszenna  2 i 1/2 szklanki");
            ingredients.add("Cukier 1 szklanka");
            ingredients.add("Miodu 3/4 szklanki");
            ingredients.add("Masło 1/2 szklanki (rozpuszczone i lekko ostudzone)");
            ingredients.add("Jajka 2 sztuki");
            ingredients.add("Kakao 2 łyżki");
            ingredients.add("Cynamon 2 łyżeczki");
            ingredients.add("Imbir 1 łyżeczka");
            ingredients.add("Goździki 1/2 łyżeczki");
            ingredients.add("Sól 1/4 łyżeczki");
            ingredients.add("Proszek do pieczenia 1 łyżeczka");
            ingredients.add("Soda oczyszczona 1/2 łyżeczki");
            ingredients.add("Migdały 1/2 szklanki (posiekane)");
            ingredients.add("Rodzynki 1/2 szklanki");

            recipe.setIngredients(ingredients);

            String imagePath = "src/main/resources/static/init_jpg/recipe3.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<String> steps = new ArrayList<>();
            steps.add("W dużym naczyniu wymieszaj mąkę, cukier, kakao, cynamon, imbir, goździki, sól, proszek do pieczenia i sodę oczyszczoną. Dokładnie wymieszaj wszystkie suche składniki.");
            steps.add("W innym naczyniu połącz miód, rozpuszczone masło i jajka. Ubij składniki trzepaczką, aż masa będzie gładka.");
            steps.add("Powoli wlej składniki mokre do naczynia z suchymi składnikami. Wymieszaj je za pomocą drewnianej łyżki lub miksera, aż powstanie jednolita masa.");
            steps.add("Dodaj posiekane migdały oraz rodzynki. Wymieszaj je równomiernie, aby były rozłożone w całym cieście.");
            steps.add("Przygotuj prostokątną formę do pieczenia, wyłożoną papierem do pieczenia lub natłuść ją masłem i posyp mąką, aby zapobiec przywieraniu ciasta.");
            steps.add("Przelej przygotowaną masę do formy, równomiernie rozprowadzając.");
            steps.add("Piecz piernik w nagrzanym piekarniku do temperatury 180°C przez około 40-45 minut. Sprawdź, czy jest gotowy, wkłuwając w środek patyczek. Jeśli patyczek wyjdzie suchy, piernik jest gotowy. Jeśli nie, kontynuuj pieczenie przez kilka minut i sprawdzaj co jakiś czas.");
            steps.add("Wyjmij piernik z piekarnika i pozostaw go w formie przez kilka minut, a następnie przenieś na kratkę do ostygnięcia.");
            steps.add("Gdy piernik całkowicie ostygnie, możesz go pokroić na kawałki i podawać. Jeśli chcesz, możesz posypać go cukrem pudrem przed podaniem.");
            recipe.setSteps(steps);

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("deser").get();
            tags.add(tag);
            tag = tagRepository.findByName("wegetariańskie").get();
            tags.add(tag);
            tag = tagRepository.findByName("łagodne").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);
        }

        // Przepisy Agnieszki
        user = userRepository.findByUsername("Agnieszka").get();
        checkRecipe = recipeRepository.findByName("Placki z serkiem");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Placki z serkiem", user, true, 30);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("serek waniliowy 400 g");
            ingredients.add("jajka 2 sztuki");
            ingredients.add("mąka 1 szklanka");
            ingredients.add("proszek do pieczenia 1 łyżeczka");
            ingredients.add("olej 2 łyżki");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Ubić pianę z białek.");
            steps.add("Dodać cukier.");
            steps.add("Wymieszać z żółtkami i serkiem waniliowym.");
            steps.add("Dodać proszek do pieczenia.");
            steps.add("Smażyć na oleju.");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/placki.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("deser").get();
            tags.add(tag);
            tag = tagRepository.findByName("wegetariańskie").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);
        }

        checkRecipe = recipeRepository.findByName("Placki ziemniaczane");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Placki ziemniaczane", user, true, 30);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("ziemniaki 0.5 kg");
            ingredients.add("mąka pszenna 0.5 łyżki");
            ingredients.add("cebula 1/4 sztuki");
            ingredients.add("jajko 1 sztuka");
            ingredients.add("sól 2 szczypty");
            ingredients.add("olej roślinny 2 łyżki");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Rozetrzeć ziemniaki na tarce, zostawić na kilka minut, odlać zebrany sok.");
            steps.add("Do ziemniaków dodać mąkę, drobno startą cebulę, jajko i sól.");
            steps.add("Masę wymieszać i smażyć małe porcje ciasta na rozgrzanym oleju na złoty kolor.");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/placki2.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));


            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("obiad").get();
            tags.add(tag);
            tag = tagRepository.findByName("polskie").get();
            tags.add(tag);
            tag = tagRepository.findByName("wegetariańskie").get();
            tags.add(tag);
            recipe.setTags(tags);


            recipeRepository.save(recipe);
            addToFolder(user, recipe);

        }

        checkRecipe = recipeRepository.findByName("Makaron w sosie cebula-masło-pomidor");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Makaron w sosie cebula-masło-pomidor", user, true, 50);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("makaron 200 g");
            ingredients.add("pomidory w puszce 800 g");
            ingredients.add("cebula 1 sztuka");
            ingredients.add("masło 70 g");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Do garnuszka wrzucić pomidorki, cebulę obraną i przekrojoną na pół oraz masełko. Dodać szczyptę cukru.");
            steps.add("Gotować na wolnym ogniu przez około 45min, mieszając i dziabdziając pomidorki o garnuszek.");
            steps.add("Wywalić cebulę - biedulka - i posolić do smaku.");
            steps.add("Ugotować i odcedzić makaron.");
            steps.add("Do sosu wrzucić makaron z odrobiną wody z makaronu i wymieszać.");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/recipe4.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));


            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("włoskie").get();
            tags.add(tag);
            tag = tagRepository.findByName("łagodne").get();
            tags.add(tag);
            tag = tagRepository.findByName("obiad").get();
            tags.add(tag);
            recipe.setTags(tags);


            recipeRepository.save(recipe);
            addToFolder(user, recipe);

        }

        checkRecipe = recipeRepository.findByName("Grochówka wojskowa tatusia");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Grochówka wojskowa tatusia", user, true, 50);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("połówki grochu łuskanego");
            ingredients.add("kiełbasa");
            ingredients.add("cebulka");
            ingredients.add("chleb tostowy na grzanki");
            ingredients.add("cebulka");
            ingredients.add("kostka rosołowa");
            ingredients.add("majeranek");
            ingredients.add("ziemniaki – opcjonalne");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Dzień przed gotowaniem namoczyć groch.");
            steps.add("Wstawiamy groch do gotowania, dodać resztę składników.");
            steps.add("Gotować na papkę i zrobić grzaneczki z chleba tostowego.");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/recipe5.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));


            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("polskie").get();
            tags.add(tag);
            tag = tagRepository.findByName("łagodne").get();
            tags.add(tag);
            tag = tagRepository.findByName("obiad").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);

        }

        // Przepisy Filipa
        user = userRepository.findByUsername("Filip").get();
        checkRecipe = recipeRepository.findByName("Klasyczne Lasagne Bolognese");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Klasyczne Lasagne Bolognese", user, true, 150);


            List<String> ingredients = new ArrayList<>();
            ingredients.add("500g mielonej wołowiny");
            ingredients.add("500g mielonego wieprzowiny");
            ingredients.add("1 cebula, drobno posiekana");
            ingredients.add("2 ząbki czosnku, posiekane");
            ingredients.add("2 marchewki, drobno pokrojone");
            ingredients.add("2 łodygi selera, drobno pokrojone");
            ingredients.add("1 puszka pomidorów krojonych");
            ingredients.add("2 łyżki koncentratu pomidorowego");
            ingredients.add("1 łyżeczka suszonego oregano");
            ingredients.add("1 łyżeczka suszonego tymianku");
            ingredients.add("1 łyżeczka suszonej bazylii");
            ingredients.add("1 łyżeczka cukru");
            ingredients.add("250ml czerwonego wina");
            ingredients.add("250ml bulionu wołowego");
            ingredients.add("250g sera ricotta");
            ingredients.add("100g startego parmezanu");
            ingredients.add("250g makaronu lasagne");
            ingredients.add("2 łyżki oliwy z oliwek");
            ingredients.add("Sól i pieprz do smaku");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("W dużym garnku rozgrzej oliwę z oliwek. Dodaj posiekaną cebulę, czosnek, marchewki i seler. Smaż na średnim ogniu przez około 5 minut, aż warzywa zmiękną.");
            steps.add("Dodaj mielone mięso wołowe i wieprzowe do garnka. Smaż, mieszając, aż mięso będzie dobrze podsmażone i rozdrobnione.");
            steps.add("Dodaj pomidory krojone, koncentrat pomidorowy, suszone zioła, cukier, czerwone wino i bulion wołowy. Doprowadź do wrzenia, a następnie zmniejsz ogień i gotuj na wolnym ogniu przez około 1,5 godziny. Mieszaj od czasu do czasu.");
            steps.add("W międzyczasie gotuj makaron lasagne według instrukcji na opakowaniu, aż będzie al dente. Odcedź i odstaw na bok.");
            steps.add("Przygotuj formę do zapiekania. Na dno formy nałóż cienką warstwę sosu mięsnego. Następnie ułóż jedną warstwę makaronu lasagne. Na makaron nałóż część sera ricotta i posyp startym parmezanem.");
            steps.add("Kontynuuj układanie warstw, używając pozostałego sosu mięsnego, makaronu lasagne, sera ricotta i parmezanu. Powinno wystarczyć na 3-4 warstwy.");
            steps.add("Zakończ na wierzchu sosu mięsnego i posyp dodatkowym parmezanem.");
            steps.add("Zapiekaj lasagne w nagrzanym piekarniku do temperatury 180°C przez około 30-35 minut, aż wierzch będzie złocisty i całość dobrze się zespoli.");
            steps.add("Po upieczeniu wyjmij z piekarnika i pozostaw do ostygnięcia przez kilka minut przed podaniem. Smacznego!");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/lasagne.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("polskie").get();
            tags.add(tag);
            tag = tagRepository.findByName("obiad").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);

        }

        checkRecipe = recipeRepository.findByName("Sałatka Caprese");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Sałatka Caprese", user, true, 15);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("4 pomidory");
            ingredients.add("250g mozzarelli");
            ingredients.add("1 pęczek świeżej bazylii");
            ingredients.add("2 łyżki oliwy z oliwek");
            ingredients.add("Sól i pieprz do smaku");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Pokrój pomidory na plastry, a mozzarellę na cienkie plasterki.");
            steps.add("Ułóż na półmisku warstwę pomidorów, następnie dodaj plasterki mozzarelli.");
            steps.add("Posyp warstwę bazylią i przypraw solą i pieprzem.");
            steps.add("Powtórz kolejne warstwy pomidorów, mozzarelli i bazylii.");
            steps.add("Na wierzch skrop sałatkę oliwą z oliwek.");
            steps.add("Przykryj i schłodź w lodówce przez około 30 minut przed podaniem.");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/caprese.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("przekąska").get();
            tags.add(tag);
            tag = tagRepository.findByName("włoskie").get();
            tags.add(tag);
            tag = tagRepository.findByName("wegetariańskie").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);
        }

        checkRecipe = recipeRepository.findByName("Tacos z Kurczakiem");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Tacos z Kurczakiem", user, true, 40);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("500g filetów z kurczaka, pokrojonych na paski");
            ingredients.add("1 cebula, pokrojona w kostkę");
            ingredients.add("2 ząbki czosnku, posiekane");
            ingredients.add("1 papryka jalapeno, posiekana (opcjonalnie)");
            ingredients.add("2 łyżki soku z limonki");
            ingredients.add("2 łyżeczki papryki w proszku");
            ingredients.add("1 łyżeczka kuminu");
            ingredients.add("1 łyżeczka oregano");
            ingredients.add("Sól i pieprz do smaku");
            ingredients.add("Tortille");
            ingredients.add("Salsa i guacamole do podania");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("W misce wymieszaj kurczaka, cebulę, czosnek, paprykę jalapeno (opcjonalnie), sok z limonki, paprykę w proszku, kumin, oregano, sól i pieprz.");
            steps.add("Odstaw na około 30 minut, aby marynować.");
            steps.add("Na rozgrzaną patelnię wrzuć kurczaka wraz z marynatą. Smaż na średnim ogniu przez około 6-8 minut, aż kurczak będzie dobrze przyrumieniony i dobrze ugotowany.");
            steps.add("Podgrzej tortille na suchej patelni przez kilka sekund z każdej strony, aby stały się miękkie i elastyczne.");
            steps.add("Nałóż na każdą tortillę smażony kurczak i dodatki takie jak salsa i guacamole.");
            steps.add("Zroluj tortille i podawaj od razu.");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/tacos.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("kolacja").get();
            tags.add(tag);
            tag = tagRepository.findByName("meksykańskie").get();
            tags.add(tag);
            tag = tagRepository.findByName("ostre").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);
        }

        //Przepisy Grzesia
        user = userRepository.findByUsername("grzesiu").get();
        checkRecipe = recipeRepository.findByName("Hui Guo Rou");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Hui Guo Rou", user, true, 45);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("Boczek 500 g");
            ingredients.add("Czosnek 3 ząbki");
            ingredients.add("Imbir 3 plastry");
            ingredients.add("Pieprz syczuański kilka ziarenek");
            ingredients.add("Pasta doubanjiang 2 łyżki");
            ingredients.add("Papryka czerwona 1 sztuka");
            ingredients.add("Papryczka chili marynowana 2 sztuki");
            ingredients.add("Por 1 sztuka");
            ingredients.add("Wino shaoxing 2 łyżki");
            ingredients.add("Sos sojowy 1 łyżka");
            ingredients.add("Ocet chiński 0.5 łyżeczki");
            ingredients.add("Cukier 1 łyżeczka");
            ingredients.add("Sól 0.5 łyżeczki");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Gotuj boczek z białą częścią pora, częścią imbiru, pieprzem syczuańskim i winem około 30 minut, aż będzie w pełni ugotowane.");
            steps.add("Ostudź dobrze boczek, w tym czasie pokrój warzywa.");
            steps.add("Pokrój boczek w cieniutkie plasterki.");
            steps.add("Dodaj pastę doubanjiang.");
            steps.add("Dodaj czosnek, resztę imbiru i marynowaną papryczkę.");
            steps.add("Dodaj sos sojowy, ocet, cukier i sól.");
            steps.add("Dodaj paprykę i zieloną część pora.");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/recipe6.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("azjatyckie").get();
            tags.add(tag);
            tag = tagRepository.findByName("obiad").get();
            tags.add(tag);
            tag = tagRepository.findByName("ostre").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);
        }

        checkRecipe = recipeRepository.findByName("Kurczak z owocami nerkowca");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Kurczak z owocami nerkowca", user, true, 30);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("Skrobia kukurydziana/ziemniaczana 1 łyżka");
            ingredients.add("Sos sojowy jasny 2 łyżki");
            ingredients.add("Wino shaoxing 2 łyżki");
            ingredients.add("Sos ostrygowy 2 łyżki");
            ingredients.add("Sos rybny 1,5 łyżki");
            ingredients.add("Olej sezamowy 2 łyżki");
            ingredients.add("Biały pieprz szczypta");
            ingredients.add("Kurczak 500 g");
            ingredients.add("Czosnek 3 ząbki");
            ingredients.add("Cebula 1 sztuka");
            ingredients.add("Zielona papryka 1 sztuka");
            ingredients.add("Woda 50 ml");
            ingredients.add("Prażone nerkowce  2 garście");
            ingredients.add("(opcjonalnie) tajska bazylia kilka listków");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Wymieszaj ze sobą skrobię, sos sojowy, ostrygowy, rybny, wino, olej sezamowy i pieprz.");
            steps.add("Zamarynuj kurczaka w odrobinie sosu.");
            steps.add("Usmaż kurczaka.");
            steps.add("Dodaj czosnek i cebulę.");
            steps.add("Dodaj paprykę.");
            steps.add("Dodaj wodę oraz sos. Doprowadź do wrzenia i mieszaj do zagęszczenia.");
            steps.add("Dodaj prażone nerkowce.");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/recipe7.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("azjatyckie").get();
            tags.add(tag);
            tag = tagRepository.findByName("obiad").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);
        }

        checkRecipe = recipeRepository.findByName("Ciasto drożdżowe");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Ciasto drożdżowe", user, true, 120);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("Drożdże 10 dg");
            ingredients.add("Mleko 3 szklanki");
            ingredients.add("Cukier 50 dg");
            ingredients.add("Cukier wanilinowy 5g");
            ingredients.add("Żółtka 12 sztuk");
            ingredients.add("Mąka 1,5 kg");
            ingredients.add("Masło 30dg");
            ingredients.add("Śmietana 2-3 łyżki");
            ingredients.add("(opcjonalnie) zapach odrobina");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Wymieszać drożdże z odrobiną ciepłego mleka i cukru.");
            steps.add("Odczekać na wyrośnięcie drożdży.");
            steps.add("Ubić żółtka z cukrem i szczyptą soli.");
            steps.add("Umieścić w dużej misce ubite jajka, mąkę, lekko podgrzane mleko, śmietanę i drożdże.");
            steps.add("Wyrabiać aż powstanie jednolita masa.");
            steps.add("Dodać rozpuszczone masło.");
            steps.add("Wyrabiać aż masa przestanie się bardzo lepić do ręki.");
            steps.add("Odstawić do wyrośnięcia.");
            steps.add("Upiec w dowolny, ulubiony sposób - z owocami, rodzynkami, dżemem około 40 minut 150-160 stopni Celjusza, przez chwilę więcej, aby podkolorować ładnie ciasto.");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/recipe8.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("deser").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);
        }

        //Przepisy Zuzi
        user = userRepository.findByUsername("Zuzanna").get();
        checkRecipe = recipeRepository.findByName("Łosoś duszony w białym winie");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Łosoś duszony w białym winie", user, true, 30);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("filet z łososia 200 g");
            ingredients.add("białe wino 1 kieliszek");
            ingredients.add("masło 20 g");
            ingredients.add("cytryna 2 plasterki");
            ingredients.add("odrobina suszonego kopereku");
            ingredients.add("pieprz 1 szczypta");
            ingredients.add("sól 1 szczypta");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Filet z łososia pozbawiamy skóry i dzielimy na mniejsze porcje..");
            steps.add("Oprószamy filety z obu stron pieprzem i solą.");
            steps.add("Smażymy kawałki z obu stron przez 1 minutę na rozgrzanym maśle.");
            steps.add("Zalewamy całość białym winem i posypujemy wierzch ryb koperkiem.");
            steps.add("Patelnię przykrywamy i dusimy rybę około 7 minut.");
            steps.add("Gotowego łososia skrapiamy sokiem z cytryny i podajemy z ulubionymi dodatkami.");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/recipe9.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("obiad").get();
            tags.add(tag);
            tag = tagRepository.findByName("łagodne").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);
        }

        checkRecipe = recipeRepository.findByName("Kurczak w sosie śmietanowym z ziołami i warzywami");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Kurczak w sosie śmietanowym z ziołami i warzywami", user, true, 40);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("4 filety z kurczaka");
            ingredients.add("1 cebula, posiekana");
            ingredients.add("2 ząbki czosnku, posiekane");
            ingredients.add("200 ml śmietany kremówki");
            ingredients.add("1 łyżka masła");
            ingredients.add("2 łyżki oliwy z oliwek");
            ingredients.add("1 papryka, pokrojona w paski");
            ingredients.add("1 marchewka, pokrojona w plasterki");
            ingredients.add("1 pietruszka, pokrojona w plasterki");
            ingredients.add("1 łyżeczka suszonego oregano");
            ingredients.add("1 łyżeczka suszonego tymianku");
            ingredients.add("Sól i pieprz do smaku");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Rozgrzej oliwę z oliwek i masło na dużym garnku lub patelni. Dodaj posiekane cebulę i czosnek i smaż przez około 2-3 minuty, aż zmiękną i staną się lekko złociste.");
            steps.add("Dodaj filety z kurczaka i obsmaż z obu stron, aż będą złociste. Następnie dodaj pokrojoną paprykę, marchewkę i pietruszkę. Smaż warzywa i kurczaka przez kilka minut, aż warzywa będą lekko miękkie.");
            steps.add("Dodaj śmietanę kremówkę do garnka z kurczakiem i warzywami. Dopraw całość solą, pieprzem, suszonym oregano i tymiankiem. Delikatnie wymieszaj, aby składniki dobrze się połączyły.");
            steps.add("Zmniejsz ogień i gotuj na wolnym ogniu przez około 10-15 minut, aż sos zgęstnieje, a kurczak będzie dobrze przegotowany.");
            steps.add("Sprawdź, czy kurczak jest odpowiednio ugotowany, a sos ma odpowiednią konsystencję. Jeśli potrzeba, dopraw danie dodatkowo solą i pieprzem.");
            steps.add("Podawaj kurczaka w sosie śmietanowym z ziołami i warzywami na talerzach. Możesz go podać z ryżem, makaronem lub ziemniakami jako dodatkiem.");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/recipe10.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("obiad").get();
            tags.add(tag);
            tag = tagRepository.findByName("łagodne").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);
        }

        checkRecipe = recipeRepository.findByName("Tradycyjne polskie kartacze");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Tradycyjne polskie kartacze", user, true, 40);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("1 kg ziemniaków");
            ingredients.add("250 g mąki ziemniaczanej");
            ingredients.add("1 jajkoe");
            ingredients.add("1 łyżeczka soli");
            ingredients.add("200 g boczku");
            ingredients.add("1 cebula");
            ingredients.add("2 łyżki oleju");
            ingredients.add("Sól i pieprz do smaku");
            ingredients.add("(opcjonalnie) śmietana i posiekany koperek do podania");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Obierz ziemniaki i ugotuj je w osolonej wodzie do miękkości. Następnie odcedź i odstaw do ostygnięcia.");
            steps.add("W międzyczasie pokrój boczek lub słoninę w drobną kostkę i podsmaż na patelni, aż będzie chrupiący. Dodaj posiekaną cebulę i smaż, aż cebula będzie miękka i lekko złocista. Odcedź nadmiar tłuszczu i odstaw do ostygnięcia.");
            steps.add("Gdy ziemniaki ostygną, zgnieć je lub przetrzyj przez tarkę na drobnych oczkach. Dodaj mąkę ziemniaczaną, jajko i sól. Wyrób ciasto, aż składniki się dobrze połączą.");
            steps.add("Na stolnicy podsypanej mąką uformuj z ciasta wałek o średnicy około 2 cm. Następnie pokrój go na kawałki o długości około 2 cm.");
            steps.add("W dużym garnku zagotuj osoloną wodę. Wrzuć pokrojone kartacze i gotuj na średnim ogniu przez około 5-7 minut, aż wypłyną na powierzchnię. Możesz gotować kartacze partiami, aby nie przeciążyć garnka.");
            steps.add("Gdy kartacze wypłyną na powierzchnię, wyłóż je łyżką cedzakową na talerz lub sitko, aby odcedzić nadmiar wody.");
            steps.add("W osobnej dużej patelni rozgrzej olej. Dodaj kartacze i smaż na złoty kolor z każdej strony.");
            steps.add("Dodaj podsmażony boczek i cebulę do smażonych kartaczy. Delikatnie wymieszaj, aby składniki się połączyły. Dopraw solą i pieprzem do smaku.");
            steps.add("Podawaj kartacze na talerzach, możesz polać je śmietaną i posypać posiekanym koperkiem.");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/recipe11.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("obiad").get();
            tags.add(tag);
            tag = tagRepository.findByName("łagodne").get();
            tags.add(tag);
            tag = tagRepository.findByName("polskie").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);
        }

        //Przepisy Doroty

        user = userRepository.findByUsername("Dorota").get();
        checkRecipe = recipeRepository.findByName("Pikantne danie z warzywami i tofu");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Pikantne danie z warzywami i tofu", user, true, 30);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("Sos sojowy 4 łyżki");
            ingredients.add("Czosnek 3 ząbki");
            ingredients.add("Imbir 1 łyżka, starty");
            ingredients.add("Mieszanka warzyw azjatyckich 300 g");
            ingredients.add("Tofu 200 g, pokrojone w kostkę");
            ingredients.add("Olej sezamowy 2 łyżki");
            ingredients.add("Olej roślinny 2 łyżki");
            ingredients.add("Mąka kukurydziana 1 łyżka");
            ingredients.add("Woda 1/4 szklanki");
            ingredients.add("Pieprz cayenne 1/2 łyżeczki");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("W misce wymieszaj sos sojowy, starty czosnek, starty imbir i olej sezamowy.");
            steps.add("Dodaj pokrojone tofu do marynaty i odstaw na 15 minut.");
            steps.add("Na rozgrzanym oleju roślinnym smaż tofu z marynatą przez kilka minut, aż stanie się złote.");
            steps.add("Dodaj do patelni mieszankę warzyw azjatyckich i smaż przez kilka minut, aż warzywa zmiękną.");
            steps.add("W małej miseczce wymieszaj mąkę kukurydzianą z wodą, dodaj do patelni i gotuj, aż sos zgęstnieje.");
            steps.add("Posyp danie pieprzem cayenne i mieszaj jeszcze przez minutę.");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/recipe12.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("pikantne").get();
            tags.add(tag);
            tag = tagRepository.findByName("azjatyckie").get();
            tags.add(tag);
            tag = tagRepository.findByName("obiad").get();
            tags.add(tag);
            tag = tagRepository.findByName("wegetariańskie").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);
        }

        checkRecipe = recipeRepository.findByName("Ostra tajska zupa z kurczakiem i makaronem ryżowym");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Ostra tajska zupa z kurczakiem i makaronem ryżowym", user, true, 30);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("Kurczak, filetowany - 300 g");
            ingredients.add("Makaron ryżowy - 100 g");
            ingredients.add("Marchewka - 1 sztuka");
            ingredients.add("Papryka czerwona - 1 sztuka");
            ingredients.add("Cebula - 1 sztuka");
            ingredients.add("Czosnek - 2 ząbki");
            ingredients.add("Kiełki fasoli mung - 100 g");
            ingredients.add("Sos sojowy - 3 łyżki");
            ingredients.add("Sos rybny - 1 łyżka");
            ingredients.add("Sos ostrygowy - 2 łyżki");
            ingredients.add("Kurkuma - 1 łyżeczka");
            ingredients.add("Kmin rzymski - 1 łyżeczka");
            ingredients.add("Cukier - 1 łyżeczka");
            ingredients.add("Woda - 500 ml");
            ingredients.add("Olej roślinny - 2 łyżki");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Pokrój kurczaka w paseczki.");
            steps.add("Marchewkę, cebulę i paprykę pokrój w cienkie paski.");
            steps.add("Czosnek posiekaj drobno.");
            steps.add("Na rozgrzanym oleju smaż czosnek i kurczaka przez kilka minut, aż mięso zmięknie.");
            steps.add("Dodaj marchewkę, cebulę i paprykę. Smaż przez kolejne kilka minut.");
            steps.add("Dodaj makaron ryżowy, sos sojowy, sos rybny, sos ostrygowy, kurkumę, kmin rzymski i cukier. Wymieszaj wszystko razem.");
            steps.add("Wlej wodę i gotuj zupę na małym ogniu przez 15 minut, aż makaron i warzywa będą miękkie.");
            steps.add("Pod koniec gotowania dodaj kiełki fasoli mung.");
            steps.add("Zupę podawaj gorącą.");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/recipe13.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("ostre").get();
            tags.add(tag);
            tag = tagRepository.findByName("azjatyckie").get();
            tags.add(tag);
            tag = tagRepository.findByName("zupa").get();
            tags.add(tag);
            tag = tagRepository.findByName("obiad").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);
        }

        checkRecipe = recipeRepository.findByName("Szybki sernik");
        if (!checkRecipe.isPresent()) {
            Recipe recipe = new Recipe("Szybki sernik", user, true, 30);

            List<String> ingredients = new ArrayList<>();
            ingredients.add("Krakersy 200 g");
            ingredients.add("Ser kremowy 250 g");
            ingredients.add("Cukier puder 100 g");
            ingredients.add("Śmietana 30% 200 ml");
            ingredients.add("Masło 100 g");
            recipe.setIngredients(ingredients);

            List<String> steps = new ArrayList<>();
            steps.add("Rozdrobnić krakersy w misce.");
            steps.add("W osobnej misce utrzeć ser kremowy z cukrem pudrem.");
            steps.add("Do utartego sera dodawać stopniowo śmietanę, cały czas mieszając.");
            steps.add("Dodać masło i dokładnie wymieszać, aż powstanie jednolita masa.");
            steps.add("Na dnie szklanki lub pucharka umieścić warstwę rozdrobnionych krakersów, a następnie warstwę masy serowej.");
            steps.add("Powtarzać warstwy do momentu wypełnienia naczynia.");
            recipe.setSteps(steps);

            String imagePath = "src/main/resources/static/init_jpg/recipe14.jpg";
            Path filePath = Paths.get(imagePath);
            recipe.setImage(Files.readAllBytes(filePath));

            List<Tag> tags = new ArrayList<>();
            Tag tag = tagRepository.findByName("deser").get();
            tags.add(tag);
            tag = tagRepository.findByName("wegetariańskie").get();
            tags.add(tag);
            tag = tagRepository.findByName("amerykańskie").get();
            tags.add(tag);
            recipe.setTags(tags);

            recipeRepository.save(recipe);
            addToFolder(user, recipe);
        }
    }


    private void addTags() {
        List<String> tags_s = new ArrayList<>();
        tags_s.add("wegetariańskie");
        tags_s.add("wegańskie");
        tags_s.add("łagodne");
        tags_s.add("pikantne");
        tags_s.add("ostre");
        tags_s.add("bez glutenu");
        tags_s.add("bez laktozy");
        tags_s.add("śniadanie");
        tags_s.add("obiad");
        tags_s.add("kolacja");
        tags_s.add("deser");
        tags_s.add("przekąska");
        tags_s.add("zupa");
        tags_s.add("włoskie");
        tags_s.add("azjatyckie");
        tags_s.add("polskie");
        tags_s.add("amerykańskie");
        tags_s.add("meksykańskie");

        Optional<Tag> tag_s;
        for (String tagName : tags_s) {
            tag_s = tagRepository.findByName(tagName);
            if (!tag_s.isPresent()) {
                tagRepository.save(new Tag(tagName));
            }
        }
    }

    private void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Optional<User> userWithId = userRepository.findByUsername(user.getUsername());
        List<Folder> folders = new ArrayList<>();
        Folder folder1 = new Folder("moje autorskie przepisy");
        Folder folder2 = new Folder("moje ulubione przepisy");
        folders.add(folder1);
        folders.add(folder2);
        userWithId.get().setFolders(folders);
        userRepository.save(userWithId.get());
    }

    private void addUsers() {
        Optional<User> checkUser = userRepository.findByUsername("Ania");
        if (!checkUser.isPresent()) {
            User user = new User("Ania", "tajnehaslo", "ania.gotuje@gmail.com");
            addUser(user);

        }

        checkUser = userRepository.findByUsername("Agnieszka");
        if (!checkUser.isPresent()) {
            User user = new User("Agnieszka", "quackquack", "agnieszka.gotuje@gmail.com");
            addUser(user);
        }

        checkUser = userRepository.findByUsername("Filip");
        if (!checkUser.isPresent()) {
            User user = new User("Filip", "filiptubyl", "filip.gotuje@gmail.com");
            addUser(user);
        }

        checkUser = userRepository.findByUsername("Zuzanna");
        if (!checkUser.isPresent()) {
            User user = new User("Zuzanna", "aaa111aaa", "zuzanka.gotuje@gmail.com");
            addUser(user);
        }

        checkUser = userRepository.findByUsername("grzesiu");
        if (!checkUser.isPresent()) {
            User user = new User("grzesiu", "grzesiu", "grzesiu.gotuje@gmail.com");
            addUser(user);
        }

        checkUser = userRepository.findByUsername("Dorota");
        if (!checkUser.isPresent()) {
            User user = new User("Dorota", "password", "dorota.gotuje@gmail.com");
            addUser(user);
        }
    }

    private void addToFolder(User user, Recipe recipe) {
        List<Folder> folders = user.getFolders();

        for (Folder folder : folders) {
            if (folder.getName().equals("moje autorskie przepisy")) {
                if (folder.getRecipes() == null) {
                    folder.setRecipes(new ArrayList<>());
                    folder.getRecipes().add(recipe);

                } else
                    folder.getRecipes().add(recipe);
            }
        }
        userRepository.save(user);
    }
}

