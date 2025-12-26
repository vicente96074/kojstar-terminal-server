package com.kojstarinnovations.terminal.storage.domain.ucextends;

import com.kojstarinnovations.terminal.commons.data.payload.commons.StorageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * StorageUC - Interface for storage use cases
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
public interface StorageUC {

    /**
     * Initialize storage
     */
    void initStorage() throws IOException;

    /**
     * Store identification
     *
     * @param file               MultipartFile
     * @param identificationType String
     * @param host               String
     * @return StorageResponse
     */
    StorageResponse storeIdentification(MultipartFile file, String identificationType, String host) throws IOException;

    /**
     * Store commodity category
     *
     * @param file MultipartFile
     * @return StorageResponse
     */
    StorageResponse storeCommodityCategory(MultipartFile file, String host) throws IOException;

    /**
     * Store brand
     *
     * @param file MultipartFile
     * @return StorageResponse
     */
    StorageResponse storeBrand(MultipartFile file, String host) throws IOException;

    /**
     * Store commodity
     *
     * @param file MultipartFile
     * @return StorageResponse
     */
    StorageResponse storeCommodity(MultipartFile file, String host) throws IOException;

    /**
     * Store storage
     *
     * @param file MultipartFile
     * @return StorageResponse
     */
    StorageResponse storageStore(MultipartFile file, String host) throws IOException;

    /**
     * Store branch storage
     *
     * @param file MultipartFile
     * @return StorageResponse
     */
    StorageResponse storageStoreBranch(MultipartFile file, String host) throws IOException;

    /**
     * Store file
     *
     * @param file               MultipartFile
     * @param host               String
     * @return StorageResponse
     */
    StorageResponse store(MultipartFile file, String host) throws IOException;
}