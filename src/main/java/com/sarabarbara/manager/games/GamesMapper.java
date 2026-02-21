package com.sarabarbara.manager.games;


import com.sarabarbara.manager.games.dtos.*;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * GamesMapper class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/02/2026
 */

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GamesMapper {

    GameAutocompleteDTO toGameAutocompleteDTOFromSteamStore(SteamStoreDTO dto);
    GameAutocompleteDTO toGameAutocompleteDTO(GamesInfo dto);
    GameListDTO toGameListDTOFromSteamStore(SteamStoreDTO dto);
    GameListDTO toGameListDTOFromGamesInfo(GamesInfo dto);
}
