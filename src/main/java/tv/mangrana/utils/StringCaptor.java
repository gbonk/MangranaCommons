package tv.mangrana.utils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tv.mangrana.exception.IncorrectWorkingReferencesException;

public class StringCaptor {

    public static String getMatchingSubstring(String fullText, String matchingRegex) {
        Pattern pattern = Pattern.compile(matchingRegex);
        Matcher matcher = pattern.matcher(fullText);
        return matcher.find() ? matcher.group(1) : null;
    }

    public static String getSeasonFolderNameFromSeason(String seasonFolderName) throws IncorrectWorkingReferencesException {
        Optional<String> typicalFormat = Optional.ofNullable(StringCaptor.getMatchingSubstring(seasonFolderName, "([Ss]\\d{2})"));
        if (typicalFormat.isPresent()) {
            return typicalFormat.get().replaceFirst("S", "Season ");
        }
        Optional<String> weirdFormat = Optional.ofNullable(StringCaptor.getMatchingSubstring(seasonFolderName, "(T\\d{1,2})"));
        if (weirdFormat.isPresent()) {
            return weirdFormat.get().replaceFirst("T", "Temporada ");
        }
        throw new IncorrectWorkingReferencesException("Couldn't determinate the season from: "+seasonFolderName);
    }

    public static String getSeasonFolderNameFromEpisode(String episodeFileName) throws IncorrectWorkingReferencesException {
        String episodeInfo = Optional.ofNullable(
                        StringCaptor.getMatchingSubstring(episodeFileName, "([Ss]\\d{2}[Ee]\\d{2})"))
                .orElseThrow(() ->
                        new IncorrectWorkingReferencesException("Couldn't determinate the episode from: "+episodeFileName));
        return "Season ".concat(episodeInfo.substring(1,3));
    }

    public static int getTMDBFromFile(String path) throws IncorrectWorkingReferencesException {
        String tmdbId = Optional.ofNullable(
                            StringCaptor.getMatchingSubstring(path.contains("/")?path.substring(path.lastIndexOf('/')):path, "\\{tmdb-(.*)\\}"))
                            .orElseThrow(() -> new IncorrectWorkingReferencesException("Couldn't find tmdb from: "+path));
        return Integer.parseInt(tmdbId);
    }

}
