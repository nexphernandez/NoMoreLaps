package com.nomorelaps.business.interfaces;

import com.nomorelaps.adapters.in.api.SmartGeocodeResponse;

/**
 * Output port for geocoding operations.
 * Decouples business logic from external mapping providers (Google, OpenStreetMap, etc.).
 *
 * @author nexphernandez
 * @version 1.0.0
 */
public interface IGeocodingProvider {
    
    /**
     * Resolves a text query into geographic coordinates.
     *
     * @param query destination text.
     * @return structured response with address and coordinates.
     */
    SmartGeocodeResponse geocode(String query);
}
