package io.github.francoiscampbell;

import io.github.francoiscampbell.api.*;
import io.github.francoiscampbell.apimodel.*;
import io.github.francoiscampbell.model.*;
import org.joda.time.*;
import retrofit.*;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Main
 * This class is a placeholder for a real application. It's just used to develop the logic
 * TODO: Refactor to make this a proper application
 */
public class Main {
    private String mockResponse = "[{\"tmsId\":\"MV005834930000\",\"rootId\":\"10815284\",\"subType\":\"Feature Film\",\"title\":\"Jurassic World\",\"releaseYear\":2015,\"releaseDate\":\"2015-06-12\",\"titleLang\":\"en\",\"descriptionLang\":\"en\",\"entityType\":\"Movie\",\"genres\":[\"Science fiction\",\"Adventure\",\"Action\",\"Thriller\"],\"longDescription\":\"Located off the coast of Costa Rica, the Jurassic World luxury resort provides a habitat for an array of genetically engineered dinosaurs, including the vicious and intelligent Indominus rex. When the massive creature escapes, it sets off a chain reaction that causes the other dinos to run amok. Now, it's up to a former military man and animal expert (Chris Pratt) to use his special skills to save two young brothers and the rest of the tourists from an all-out, prehistoric assault.\",\"shortDescription\":\"An animal expert must save tourists on an island from rampaging, genetically engineered dinosaurs.\",\"topCast\":[\"Chris Pratt\",\"Bryce Dallas Howard\",\"Irrfan Khan\"],\"directors\":[\"Colin Trevorrow\"],\"officialUrl\":\"http://www.jurassicworld.com/\",\"qualityRating\":{\"ratingsBody\":\"TMS\",\"value\":\"3\"},\"ratings\":[{\"body\":\"Motion Picture Association of America\",\"code\":\"PG-13\"}],\"advisories\":[\"Adult Situations\",\"Violence\"],\"runTime\":\"PT02H04M\",\"preferredImage\":{\"width\":\"240\",\"height\":\"360\",\"caption\":{\"content\":\"Jurassic World (2015)\",\"lang\":\"en\"},\"uri\":\"assets/p10815284_p_v5_ab.jpg\",\"category\":\"Poster Art\",\"text\":\"yes\",\"primary\":\"true\"},\"showtimes\":[{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T13:00\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T16:00\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T18:50\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T21:40\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"10257\",\"name\":\"Carlton Cinema\"},\"dateTime\":\"2015-07-25T13:25\",\"barg\":false},{\"theatre\":{\"id\":\"10257\",\"name\":\"Carlton Cinema\"},\"dateTime\":\"2015-07-25T16:10\",\"barg\":false},{\"theatre\":{\"id\":\"10257\",\"name\":\"Carlton Cinema\"},\"dateTime\":\"2015-07-25T18:55\",\"barg\":false},{\"theatre\":{\"id\":\"10257\",\"name\":\"Carlton Cinema\"},\"dateTime\":\"2015-07-25T21:35\",\"barg\":false},{\"theatre\":{\"id\":\"6738\",\"name\":\"Rainbow Cinemas Market Square\"},\"dateTime\":\"2015-07-25T15:50\",\"barg\":false},{\"theatre\":{\"id\":\"6738\",\"name\":\"Rainbow Cinemas Market Square\"},\"dateTime\":\"2015-07-25T21:25\",\"barg\":false},{\"theatre\":{\"id\":\"641\",\"name\":\"TheDocks.com Drive-In\"},\"dateTime\":\"2015-07-25T21:20\",\"barg\":false},{\"theatre\":{\"id\":\"5675\",\"name\":\"SilverCity Yonge-Eglinton Cinemas\"},\"dateTime\":\"2015-07-25T18:50\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5675\",\"name\":\"SilverCity Yonge-Eglinton Cinemas\"},\"dateTime\":\"2015-07-25T21:40\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false}]},{\"tmsID\":\"MV007058380000\",\"rootId\":\"11424499\",\"subType\":\"Feature Film\",\"title\":\"The Gallows\",\"releaseYear\":2015,\"releaseDate\":\"2015-07-10\",\"titleLang\":\"en\",\"descriptionLang\":\"en\",\"entityType\":\"Movie\",\"genres\":[\"Thriller\",\"Horror\"],\"longDescription\":\"In 1993, a freak accident involving a noose kills teenager Charlie Grimille during a high-school production of \\\"The Gallows.\\\" Twenty years later, on the eve of the play's revival, students Reese (Reese Mishler), Pfeifer (Pfeifer Brown), Ryan (Ryan Shoos) and Cassidy become trapped in the auditorium, with no way of calling for help. A night of terror awaits the four friends as they face the wrath of a malevolent and vengeful spirit. It seems Charlie will have his curtain call after all.\",\"shortDescription\":\"The vengeful spirit of a dead teen terrorizes four high-school students trapped in an auditorium.\",\"topCast\":[\"Reese Mishler\",\"Pfeifer Brown\",\"Ryan Shoos\"],\"directors\":[\"Chris Lofing\",\"Travis Cluff\"],\"officialUrl\":\"http://thegallowsmovie.com/\",\"qualityRating\":{\"ratingsBody\":\"TMS\",\"value\":\"1.5\"},\"ratings\":[{\"body\":\"Motion Picture Association of America\",\"code\":\"R\"}],\"advisories\":[\"Adult Situations\",\"Violence\"],\"runTime\":\"PT01H21M\",\"preferredImage\":{\"width\":\"240\",\"height\":\"360\",\"caption\":{\"content\":\"The Gallows (2015)\",\"lang\":\"en\"},\"uri\":\"assets/p11424499_p_v5_ab.jpg\",\"category\":\"Poster Art\",\"text\":\"yes\",\"primary\":\"true\"},\"showtimes\":[{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T13:00\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T20:20\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false}]},{\"tmsID\":\"MV006074350000\",\"rootId\":\"10951814\",\"subType\":\"Feature Film\",\"title\":\"Ant-Man\",\"releaseYear\":2015,\"releaseDate\":\"2015-07-17\",\"titleLang\":\"en\",\"descriptionLang\":\"en\",\"entityType\":\"Movie\",\"genres\":[\"Adventure\",\"Action\",\"Comedy\"],\"longDescription\":\"Forced out of his own company by former protégé Darren Cross, Dr. Hank Pym (Michael Douglas) recruits the talents of Scott Lang (Paul Rudd), a master thief just released from prison. Lang becomes Ant-Man, trained by Pym and armed with a suit that allows him to shrink in size, possess superhuman strength and control an army of ants. The miniature hero must use his new skills to prevent Cross, also known as Yellowjacket, from perfecting the same technology and using it as a weapon for evil.\",\"shortDescription\":\"Ant-Man must prevent Yellowjacket from perfecting his shrinking technology as a weapon for evil.\",\"topCast\":[\"Paul Rudd\",\"Michael Douglas\",\"Evangeline Lilly\"],\"directors\":[\"Peyton Reed\"],\"officialUrl\":\"http://marvel.com/antman\",\"qualityRating\":{\"ratingsBody\":\"TMS\",\"value\":\"3\"},\"ratings\":[{\"body\":\"Motion Picture Association of America\",\"code\":\"PG-13\"}],\"advisories\":[\"Adult Situations\",\"Violence\"],\"runTime\":\"PT01H57M\",\"preferredImage\":{\"width\":\"240\",\"height\":\"360\",\"caption\":{\"content\":\"Ant-Man (2015)\",\"lang\":\"en\"},\"uri\":\"assets/p10951814_p_v5_ac.jpg\",\"category\":\"Poster Art\",\"text\":\"yes\",\"primary\":\"true\"},\"showtimes\":[{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T13:10\",\"quals\":\"Closed Caption & Descriptive Video|No Passes\",\"barg\":false},{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T15:50\",\"quals\":\"Closed Caption & Descriptive Video|No Passes\",\"barg\":false},{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T18:30\",\"quals\":\"Closed Caption & Descriptive Video|No Passes\",\"barg\":false},{\"theatre\":{\"id\":\"9035\",\"name\":\"Cineplex Cinemas Varsity and VIP\"},\"dateTime\":\"2015-07-25T17:00\",\"quals\":\"No Passes\",\"barg\":false},{\"theatre\":{\"id\":\"9035\",\"name\":\"Cineplex Cinemas Varsity and VIP\"},\"dateTime\":\"2015-07-25T22:20\",\"quals\":\"VIP 19+|No Passes\",\"barg\":false},{\"theatre\":{\"id\":\"6738\",\"name\":\"Rainbow Cinemas Market Square\"},\"dateTime\":\"2015-07-25T13:05\",\"barg\":false},{\"theatre\":{\"id\":\"6738\",\"name\":\"Rainbow Cinemas Market Square\"},\"dateTime\":\"2015-07-25T16:05\",\"barg\":false},{\"theatre\":{\"id\":\"6738\",\"name\":\"Rainbow Cinemas Market Square\"},\"dateTime\":\"2015-07-25T18:55\",\"barg\":false},{\"theatre\":{\"id\":\"6738\",\"name\":\"Rainbow Cinemas Market Square\"},\"dateTime\":\"2015-07-25T21:30\",\"barg\":false},{\"theatre\":{\"id\":\"5675\",\"name\":\"SilverCity Yonge-Eglinton Cinemas\"},\"dateTime\":\"2015-07-25T16:20\",\"quals\":\"Closed Caption & Descriptive Video|No Passes\",\"barg\":false},{\"theatre\":{\"id\":\"10297\",\"name\":\"Humber Cinemas\"},\"dateTime\":\"2015-07-25T13:30\",\"barg\":false},{\"theatre\":{\"id\":\"10297\",\"name\":\"Humber Cinemas\"},\"dateTime\":\"2015-07-25T16:10\",\"barg\":false},{\"theatre\":{\"id\":\"10297\",\"name\":\"Humber Cinemas\"},\"dateTime\":\"2015-07-25T19:00\",\"barg\":false},{\"theatre\":{\"id\":\"10297\",\"name\":\"Humber Cinemas\"},\"dateTime\":\"2015-07-25T21:35\",\"barg\":false}]},{\"tmsID\":\"MV007104600000\",\"rootId\":\"11447189\",\"subType\":\"Feature Film\",\"title\":\"Southpaw\",\"releaseYear\":2015,\"releaseDate\":\"2015-07-24\",\"titleLang\":\"en\",\"descriptionLang\":\"en\",\"entityType\":\"Movie\",\"genres\":[\"Drama\"],\"longDescription\":\"Billy \\\"The Great\\\" Hope (Jake Gyllenhaal), the reigning junior middleweight boxing champion, has an impressive career, a loving wife and daughter, and a lavish lifestyle. However, when tragedy strikes, Billy hits rock bottom, losing his family, his house and his manager. He soon finds an unlikely savior in Tick Willis (Forest Whitaker), a former fighter who trains the city's toughest amateur boxers. With his future on the line, Hope fights to reclaim the trust of those he loves the most.\",\"shortDescription\":\"A retired boxer (Forest Whitaker) helps a down-and-out fighter (Jake Gyllenhaal) seek redemption.\",\"topCast\":[\"Jake Gyllenhaal\",\"Forest Whitaker\",\"Naomie Harris\"],\"directors\":[\"Antoine Fuqua\"],\"officialUrl\":\"http://southpawfilm.com/\",\"ratings\":[{\"body\":\"Motion Picture Association of America\",\"code\":\"R\"}],\"advisories\":[\"Adult Language\",\"Adult Situations\",\"Violence\"],\"runTime\":\"PT02H03M\",\"preferredImage\":{\"width\":\"240\",\"height\":\"360\",\"caption\":{\"content\":\"Southpaw (2015)\",\"lang\":\"en\"},\"uri\":\"assets/p11447189_p_v5_ab.jpg\",\"category\":\"Poster Art\",\"text\":\"yes\",\"primary\":\"true\"},\"showtimes\":[{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T13:20\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T13:50\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T15:00\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T16:10\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T16:50\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T19:00\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T19:50\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T22:00\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5683\",\"name\":\"Scotiabank Theatre Toronto\"},\"dateTime\":\"2015-07-25T22:50\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"9035\",\"name\":\"Cineplex Cinemas Varsity and VIP\"},\"dateTime\":\"2015-07-25T12:30\",\"quals\":\"VIP 19+\",\"barg\":false},{\"theatre\":{\"id\":\"9035\",\"name\":\"Cineplex Cinemas Varsity and VIP\"},\"dateTime\":\"2015-07-25T12:45\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"9035\",\"name\":\"Cineplex Cinemas Varsity and VIP\"},\"dateTime\":\"2015-07-25T15:30\",\"quals\":\"VIP 19+\",\"barg\":false},{\"theatre\":{\"id\":\"9035\",\"name\":\"Cineplex Cinemas Varsity and VIP\"},\"dateTime\":\"2015-07-25T15:50\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"9035\",\"name\":\"Cineplex Cinemas Varsity and VIP\"},\"dateTime\":\"2015-07-25T18:30\",\"quals\":\"VIP 19+\",\"barg\":false},{\"theatre\":{\"id\":\"9035\",\"name\":\"Cineplex Cinemas Varsity and VIP\"},\"dateTime\":\"2015-07-25T19:00\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"9035\",\"name\":\"Cineplex Cinemas Varsity and VIP\"},\"dateTime\":\"2015-07-25T21:30\",\"quals\":\"VIP 19+\",\"barg\":false},{\"theatre\":{\"id\":\"9035\",\"name\":\"Cineplex Cinemas Varsity and VIP\"},\"dateTime\":\"2015-07-25T22:05\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5675\",\"name\":\"SilverCity Yonge-Eglinton Cinemas\"},\"dateTime\":\"2015-07-25T13:15\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5675\",\"name\":\"SilverCity Yonge-Eglinton Cinemas\"},\"dateTime\":\"2015-07-25T16:10\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5675\",\"name\":\"SilverCity Yonge-Eglinton Cinemas\"},\"dateTime\":\"2015-07-25T19:20\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false},{\"theatre\":{\"id\":\"5675\",\"name\":\"SilverCity Yonge-Eglinton Cinemas\"},\"dateTime\":\"2015-07-25T22:15\",\"quals\":\"Closed Caption & Descriptive Video\",\"barg\":false}]},{\"tmsID\":\"MV007805370000\",\"rootId\":\"11936866\",\"subType\":\"Feature Film\",\"title\":\"The Breakup Playlist\",\"releaseYear\":2015,\"releaseDate\":\"2015-07-01\",\"titleLang\":\"en\",\"descriptionLang\":\"en\",\"entityType\":\"Movie\",\"genres\":[\"Drama\",\"Romance\"],\"shortDescription\":\"A musician and a rock singer develop feelings for each other as they collaborate on a song.\",\"longDescription\":\"A musician and a rock singer develop feelings for each other as they collaborate on a song.\",\"topCast\":[\"Teddy Corpuz\",\"Sarah Geronimo\",\"Rio Locsin\"],\"directors\":[\"Dan Villegas\"],\"runTime\":\"PT01H30M\",\"preferredImage\":{\"uri\":\"tvbanners/generic/generic_tvbanners_v5.png\"},\"showtimes\":[{\"theatre\":{\"id\":\"8798\",\"name\":\"Famous Players Canada Square Cinemas\"},\"dateTime\":\"2015-07-25T13:35\",\"barg\":false},{\"theatre\":{\"id\":\"8798\",\"name\":\"Famous Players Canada Square Cinemas\"},\"dateTime\":\"2015-07-25T16:25\",\"barg\":false},{\"theatre\":{\"id\":\"8798\",\"name\":\"Famous Players Canada Square Cinemas\"},\"dateTime\":\"2015-07-25T19:05\",\"barg\":false},{\"theatre\":{\"id\":\"8798\",\"name\":\"Famous Players Canada Square Cinemas\"},\"dateTime\":\"2015-07-25T21:40\",\"barg\":false}]},{\"tmsID\":\"MV005341260000\",\"rootId\":\"10401509\",\"subType\":\"Feature Film\",\"title\":\"Infinitely Polar Bear\",\"releaseYear\":2014,\"releaseDate\":\"2014-01-18\",\"titleLang\":\"en\",\"descriptionLang\":\"en\",\"entityType\":\"Movie\",\"genres\":[\"Comedy drama\"],\"longDescription\":\"In Boston, a bipolar individual (Mark Ruffalo) takes over sole responsibility for his two spirited daughters while his wife (Zoe Saldana) attends graduate school in New York.\",\"shortDescription\":\"A bipolar man has sole responsibility for his daughters while his wife attends graduate school.\",\"topCast\":[\"Mark Ruffalo\",\"Zoe Saldana\",\"Imogene Wolodarsky\"],\"directors\":[\"Maya Forbes\"],\"officialUrl\":\"http://sonyclassics.com/infinitelypolarbear/\",\"qualityRating\":{\"ratingsBody\":\"TMS\",\"value\":\"3\"},\"ratings\":[{\"body\":\"Motion Picture Association of America\",\"code\":\"R\"}],\"advisories\":[\"Adult Language\",\"Adult Situations\"],\"runTime\":\"PT01H27M\",\"preferredImage\":{\"width\":\"240\",\"height\":\"360\",\"caption\":{\"content\":\"Infinitely Polar Bear (2014)\",\"lang\":\"en\"},\"uri\":\"assets/p10401509_p_v5_aa.jpg\",\"category\":\"Poster Art\",\"text\":\"yes\",\"primary\":\"true\"},\"showtimes\":[{\"theatre\":{\"id\":\"8798\",\"name\":\"Famous Players Canada Square Cinemas\"},\"dateTime\":\"2015-07-25T13:40\",\"barg\":false},{\"theatre\":{\"id\":\"8798\",\"name\":\"Famous Players Canada Square Cinemas\"},\"dateTime\":\"2015-07-25T16:20\",\"barg\":false},{\"theatre\":{\"id\":\"8798\",\"name\":\"Famous Players Canada Square Cinemas\"},\"dateTime\":\"2015-07-25T18:35\",\"barg\":false},{\"theatre\":{\"id\":\"8798\",\"name\":\"Famous Players Canada Square Cinemas\"},\"dateTime\":\"2015-07-25T21:10\",\"barg\":false}]},{\"tmsID\":\"MV006816280000\",\"rootId\":\"11326539\",\"subType\":\"Feature Film\",\"title\":\"Woman in Gold\",\"releaseYear\":2015,\"releaseDate\":\"2015-04-01\",\"titleLang\":\"en\",\"descriptionLang\":\"en\",\"entityType\":\"Movie\",\"genres\":[\"Docudrama\"],\"longDescription\":\"Sixty years after fleeing Vienna, Maria Altmann (Helen Mirren), an elderly Jewish woman, attempts to reclaim family possessions that were seized by the Nazis. Among them is a famous portrait of Maria's beloved Aunt Adele: Gustave Klimt's \\\"Portrait of Adele Bloch-Bauer I.\\\" With the help of young lawyer Randy Schoeberg (Ryan Reynolds), Maria embarks upon a lengthy legal battle to recover this painting and several others, but it will not be easy, for Austria considers them national treasures.\",\"shortDescription\":\"A Jewish survivor of World War II sues the Austrian government for her family's stolen artwork.\",\"topCast\":[\"Helen Mirren\",\"Ryan Reynolds\",\"Daniel Brühl\"],\"directors\":[\"Simon Curtis\"],\"qualityRating\":{\"ratingsBody\":\"TMS\",\"value\":\"2.5\"},\"ratings\":[{\"body\":\"Motion Picture Association of America\",\"code\":\"PG-13\"}],\"advisories\":[\"Adult Language\",\"Adult Situations\"],\"runTime\":\"PT01H51M\",\"preferredImage\":{\"width\":\"240\",\"height\":\"360\",\"caption\":{\"content\":\"Woman in Gold (2015)\",\"lang\":\"en\"},\"uri\":\"assets/p11326539_p_v5_ac.jpg\",\"category\":\"Poster Art\",\"text\":\"yes\",\"primary\":\"true\"},\"showtimes\":[{\"theatre\":{\"id\":\"2523\",\"name\":\"Mt. Pleasant Theatre\"},\"dateTime\":\"2015-07-25T16:00\",\"barg\":false},{\"theatre\":{\"id\":\"2523\",\"name\":\"Mt. Pleasant Theatre\"},\"dateTime\":\"2015-07-25T20:50\",\"barg\":false}]},{\"tmsID\":\"MV007157780000\",\"rootId\":\"11478966\",\"subType\":\"Feature Film\",\"title\":\"Phoenix\",\"releaseYear\":2014,\"releaseDate\":\"2014-09-05\",\"titleLang\":\"en\",\"descriptionLang\":\"en\",\"entityType\":\"Movie\",\"genres\":[\"Historical drama\"],\"longDescription\":\"After undergoing reconstructive surgery, a concentration camp survivor (Nina Hoss) tries to find out if her husband (Ronald Zehrfeld) betrayed her to the Nazis.\",\"shortDescription\":\"A concentration camp survivor tries to find out if her husband betrayed her to the Nazis.\",\"topCast\":[\"Nina Hoss\",\"Ronald Zehrfeld\",\"Nina Kunzendorf\"],\"directors\":[\"Christian Petzold\"],\"ratings\":[{\"body\":\"Motion Picture Association of America\",\"code\":\"PG-13\"}],\"advisories\":[\"Adult Situations\",\"Violence\"],\"runTime\":\"PT01H38M\",\"preferredImage\":{\"width\":\"240\",\"height\":\"360\",\"caption\":{\"content\":\"Phoenix (2014)\",\"lang\":\"en\"},\"uri\":\"assets/p11478966_p_v5_aa.jpg\",\"category\":\"Poster Art\",\"text\":\"yes\",\"primary\":\"true\"},\"showtimes\":[{\"theatre\":{\"id\":\"2523\",\"name\":\"Mt. Pleasant Theatre\"},\"dateTime\":\"2015-07-25T18:30\",\"barg\":false}]},{\"tmsID\":\"MV003636140000\",\"rootId\":\"8718307\",\"subType\":\"Feature Film\",\"title\":\"Alvin and the Chipmunks: Chipwrecked\",\"releaseYear\":2011,\"releaseDate\":\"2011-12-16\",\"titleLang\":\"en\",\"descriptionLang\":\"en\",\"entityType\":\"Movie\",\"genres\":[\"Animated\",\"Adventure\",\"Comedy\"],\"longDescription\":\"Dave Seville (Jason Lee), the Chipmunks (Justin Long, Matthew Gray Gubler, Jesse McCartney) and the Chipettes are taking a luxury cruise to the International Music Awards. Of course, Alvin cannot resist the urge to create havoc, and the singing rodents soon find themselves marooned on a seemingly deserted island. While Dave and an unlikely ally launch a search, Alvin and company discover that they are not alone on the island, as they first thought.\",\"shortDescription\":\"Alvin and company, along with the Chipettes, become marooned on a not-so-deserted island.\",\"topCast\":[\"Jason Lee\",\"David Cross\",\"Jenny Slate\"],\"directors\":[\"Mike Mitchell\"],\"officialUrl\":\"http://www.munkyourself.com/\",\"qualityRating\":{\"ratingsBody\":\"TMS\",\"value\":\"1\"},\"ratings\":[{\"body\":\"Motion Picture Association of America\",\"code\":\"G\"}],\"runTime\":\"PT01H27M\",\"animation\":\"Live action/animated\",\"preferredImage\":{\"width\":\"240\",\"height\":\"360\",\"caption\":{\"content\":\"Alvin and the Chipmunks: Chipwrecked (2011)\",\"lang\":\"en\"},\"uri\":\"assets/p8718307_p_v5_ag.jpg\",\"category\":\"Poster Art\",\"text\":\"yes\",\"primary\":\"true\"},\"showtimes\":[{\"theatre\":{\"id\":\"5675\",\"name\":\"SilverCity Yonge-Eglinton Cinemas\"},\"dateTime\":\"2015-07-25T11:00\",\"barg\":false}]}]\n";


    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        ScheduleGenerator.Builder builder = getScheduleGenarator();
        mainLoop(builder);
    }

    private void mainLoop(ScheduleGenerator.Builder builder) {
        do {
            chooseParameters(builder);
            ScheduleGenerator generator = builder.build();
            List<Movie> desiredMovies = selectMovies(generator.getAllMovies());
            List<Schedule> possibleSchedules = generator.generateSchedules(desiredMovies);
            printSchedules(possibleSchedules);
        } while (!quit());
    }

    private void chooseParameters(ScheduleGenerator.Builder builder) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sort by delay?");
        boolean sortByDelay = scanner.nextBoolean();

        System.out.println("Include previews length?");
        boolean includePreviewsLength = scanner.nextBoolean();

        System.out.println("Maximum overlap minutes:");
        int maxOverlap = scanner.nextInt();

        System.out.println("Maximum delay minutes between movies:");
        int maxIndividualDelay = scanner.nextInt();

        System.out.println("Maximum total delay minutes:");
        int maxTotalDelay = scanner.nextInt();

        builder.sortByDelay(sortByDelay)
               .includePreviewsLength(includePreviewsLength)
               .maxOverlap(Duration.standardMinutes(maxOverlap))
               .maxIndividualDelay(Duration.standardMinutes(maxIndividualDelay))
               .maxTotalDelay(Duration.standardMinutes(maxTotalDelay));
    }

    private boolean quit() {
        System.out.println("Type 'q' to quit");
        return new Scanner(System.in).next()
                                     .startsWith("q");
    }

    private ScheduleGenerator.Builder getScheduleGenarator() {
        String currentDate = LocalDate.now().toString();
        OnConnectApiRequest request = new OnConnectApiRequest.Builder(currentDate)
                .apiKey(ApiKey.API_KEY)
                .postcode("M5T1N5")
//                .radiusUnit(OnConnectApiRequest.RadiusUnit.KM)
                .logLevel(RestAdapter.LogLevel.FULL)
                .mockResponse(mockResponse)
                .build();

        return request.execute();
    }


    private List<Movie> selectMovies(List<Movie> movies) {
        System.out.println("Select movie: ");
        int i = 0;
        for (Movie m : movies) {
            i++;
            System.out.println("\t" + i + ") " + m.getTitle());

        }

        List<Movie> desiredMovies = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        String selections = s.nextLine();
        String[] selectionsArray = selections.split(",");
        for (String sel : selectionsArray) {
            int selection = Integer.parseInt(sel) - 1; //off by one error
            desiredMovies.add(movies.get(selection));
        }
        return desiredMovies;
    }

    private void printSchedules(List<Schedule> possibleSchedules) {
        System.out.println(possibleSchedules.size() + " schedules generated:");

        int i = 0;
        for (Schedule schedule : possibleSchedules) {
            i++;
            System.out.println("Schedule " + i + " at " + schedule.getTheatre()
                                                                  .getName() + ":");

            Map<Showtime, Duration> delays = schedule.getDelays();
            Duration minDelay;
            Duration maxDelay;
            Duration difference;
            if (delays.size() > 0) {
                minDelay = Collections.min(delays.values());
                maxDelay = Collections.max(delays.values());
            } else {
                minDelay = new Duration(0);
                maxDelay = new Duration(0);
            }
            difference = maxDelay.minus(minDelay);

            for (Showtime showtime : schedule.getShowtimes()) {
                System.out.println("\t" + showtime.toFriendlyString(true));
                Duration delay = schedule.getDelayAfterShowtime(showtime);
                if (delay != null) {
                    float ratio = MoreMath.protectedDivide(delay.minus(minDelay)
                                                                .getMillis(), difference.getMillis(), 1);

                    Color lerp = colorLerp(Color.GREEN, Color.RED, ratio);
//                    System.out.println("lerp = " + lerp);
                    String plural = delay.getStandardMinutes() == 1 ? " minute" : " minutes";
                    System.out.println("\tDelay of " + delay.getStandardMinutes() + plural);
                }
            }
        }
    }

    private Color colorLerp(Color min, Color max, float ratio) {
        float[] minHsb = Color.RGBtoHSB(min.getRed(), min.getGreen(), min.getBlue(), null);
        float[] maxHsb = Color.RGBtoHSB(max.getRed(), max.getGreen(), max.getBlue(), null);
        float[] hsb = hsbLerp(minHsb, maxHsb, ratio);
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }

    private float[] hsbLerp(float[] minHsb, float[] maxHsb, float ratio) {
        float inverseRatio = 1 - ratio;
        float h = minHsb[0] * ratio + maxHsb[0] * inverseRatio;
        float s = minHsb[1] * ratio + maxHsb[1] * inverseRatio;
        float b = minHsb[2] * ratio + maxHsb[2] * inverseRatio;
        return new float[]{h, s, b};
    }
}
