import de.umass.lastfm.*;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Scanner;

public class Test {
    public static void main(String [] args){
        String key = "5a78b2b840811b4ab8d26309e2b68ee6"; //this is the key used in the Last.fm API examples
        Scanner s = new Scanner(System.in);
        String user = "Roy19110";

        System.out.println("What is your Last FM Name?");
        user = s.nextLine();
        Chart<Artist> chart = User.getWeeklyArtistChart(user, 5, key);

        System.out.println("What period of time would you like to observe?");
        System.out.println("A: Last week  B: Last month  C: Last 3 months  D: Last 6 months  E: Last year  F: Overall");
        period = s.nextLine();


        while (!(period.equals("A")||period.equals("a")||period.equals("B")||period.equals("b")||period.equals("C")||
                period.equals("c")||period.equals("D")||period.equals("d")||period.equals("E")||period.equals("e")||period.equals("F")||period.equals("f"))){
            System.out.println("Try again with valid input!");
            System.out.println("What period of time would you like to observe?");
            System.out.println("A: Last week  B: Last month  C: Last 3 months  D: Last 6 months  E: Last year  F: Overall");
            period = s.nextLine();
        }


        Collection<Artist> Artists = User.getTopArtists(user, Period.OVERALL, key);
        Collection<Track> tracks = User.getTopTracks(user, Period.OVERALL, key);
        Collection<Album> albums = User.getTopAlbums(user, Period.OVERALL, key);
        Collection<Artist> CountryA = Geo.getTopArtists("United States", key);
        Collection<Track> CountryT = Geo.getTopTracks("United States", key);
        try{
            
           Artists = User.getTopArtists(user, Period.OVERALL, key);
            tracks = User.getTopTracks(user, Period.OVERALL, key);
            albums = User.getTopAlbums(user, Period.OVERALL, key);
            CountryA = Geo.getTopArtists("United States", key);
            CountryT = Geo.getTopTracks("United States", key);
        } catch(Exception e){
            System.out.println("INVALID NAME");
        }



        if(period.equals("A")){
            Artists = User.getTopArtists(user, Period.WEEK, key);
            tracks = User.getTopTracks(user, Period.WEEK, key);
            albums = User.getTopAlbums(user, Period.WEEK, key);
            period = "last week";
        } else if(period.equals("B")){
            Artists = User.getTopArtists(user, Period.ONE_MONTH, key);
            tracks = User.getTopTracks(user, Period.ONE_MONTH, key);
            albums = User.getTopAlbums(user, Period.ONE_MONTH, key);
            period = "last month";
        } else if (period.equals("C")) {
            Artists = User.getTopArtists(user, Period.THREE_MONTHS, key);
            tracks = User.getTopTracks(user, Period.THREE_MONTHS, key);
            albums = User.getTopAlbums(user, Period.THREE_MONTHS, key);
            period = "Last 3 months";
        } else if(period.equals("D")) {
            Artists = User.getTopArtists(user, Period.SIX_MONTHS, key);
            tracks = User.getTopTracks(user, Period.SIX_MONTHS, key);
            albums = User.getTopAlbums(user, Period.SIX_MONTHS, key);
            period = "last six months";
        }
        else if(period.equals("E")) {
            Artists = User.getTopArtists(user, Period.TWELVE_MONTHS, key);
            tracks = User.getTopTracks(user, Period.TWELVE_MONTHS, key);
            albums = User.getTopAlbums(user, Period.TWELVE_MONTHS, key);
            period = "last year";
        }
        else if(period.equals("F")) {
            Artists = User.getTopArtists(user, Period.OVERALL, key);
            tracks = User.getTopTracks(user, Period.OVERALL, key);
            albums = User.getTopAlbums(user, Period.OVERALL, key);
            period = "overall";
        }


        DateFormat format = DateFormat.getDateInstance();
        String from = format.format(chart.getFrom());
        String to = format.format(chart.getTo());

        s.nextLine();
        //System.out.printf("Charts for %s for the week from %s to %s:%n", user, from, to);



        System.out.println("Enter for your Top 5 songs of " + period);
        s.nextLine();


        String[] countryArtists = new String[50];
        int z=0;
        for (Artist artist: CountryA) {
            countryArtists[z] = artist.getName();
            z++;
        }

        String[] countryTracks = new String[50];
        int q=0;
        for (Track track: CountryT) {
            countryTracks[q] = track.getName();
            q++;
        }

        String[] topTrackNames = new String[50];
        int k=0;
        for (Track track : tracks) {
            System.out.println(track.getName());
        }


        String[] topArtistNames = new String[50];

        //string array that replaces each original artist in topArtistNames with the first recommended artist for each artist.
        String[] topRecArtists = new String[50];



        //Here we are converting the top artists of the user from a collection into an Arraylist, so that we can iterate specific intervals of top artists
        //We are doing the same for recommendedArtists, which is an arraylist of the similar artists to a specific artist in top artists arraylist
        ArrayList<Artist> userArtists = new ArrayList<>();
        ArrayList<Artist> recommendedArtists;
        for (Artist artist : Artists) {
            //System.out.println(track.getName());
            userArtists.add(artist);
        }

        //getSimilar(String artist, String apiKey)

        Collection<Artist> recArtistCollection = new ArrayList<>();
        for (int i=0; i<5; i++){
            Artist temp = userArtists.get(i);
            recArtistCollection = temp.getSimilar(temp.getName(), 2,"5a78b2b840811b4ab8d26309e2b68ee6");
            //System.out.println(recArtistCollection);
//            for (Artist artists: recArtistCollection){
//                recommendedArtists.add(artists);
//            }

            recommendedArtists = new ArrayList<>(recArtistCollection);
            topRecArtists[i] = recommendedArtists.get(0).getName();

            recommendedArtists.clear();
        }




        String[] topAlbumNames = new String[50];
        int u = 0;
        for(Album album : albums){
            topAlbumNames[u] = album.getName() + " by " + album.getArtist();
           u++;
        }

        int l=0;
        int[] topTrackPlaycount = new int[50];
        int totalPlayCount = 0;
        for (Track Track: tracks){
            topTrackPlaycount[l] = Track.getPlaycount();
            totalPlayCount += Track.getPlaycount();
            l++;
        }

        int p=0;
        for (Artist artist : Artists) {
            topArtistNames[p] = artist.getName();
            p++;
        }


        for (int i=0; i<5; i++){
            System.out.println(topTrackNames[i] + " was played " + topTrackPlaycount[i] + " times");
        }

        System.out.println();
        System.out.println();
        System.out.println("Next line for your Top 5 Artists of " + period);
        System.out.println("------------------------------------------------------------");

        s.nextLine();
        for (int i=0; i<5; i++){
            System.out.println(topArtistNames[i]);
        }

        System.out.println();
        System.out.println();
        System.out.println("Next line for your Top 5 ALBUMS of " + period);
        System.out.println("------------------------------------------------------------");

        s.nextLine();
        for (int i=0; i<5; i++){
            System.out.println(topAlbumNames[i]);
        }

        System.out.println();
        System.out.println();
        System.out.println("Reccomended artist and songs");
        System.out.println("------------------------------------------------------------");

        s.nextLine();
        for(int i=0; i<recArtistNum; i++){
            System.out.println(topRecArtists.get(i).getName());
        }

    }
}
