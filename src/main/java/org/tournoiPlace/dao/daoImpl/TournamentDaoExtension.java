package org.tournoiPlace.dao.daoImpl;

import org.tournoiPlace.dao.TournamentDao;

public abstract class TournamentDaoExtension  implements TournamentDao {

    @Override
    public long calculerDureeEstimeeTournoi(Long tournoiId, int nbEquipes, long dureeMoyenneMatch, long tempsPause) {
        return 0;
    }
}

