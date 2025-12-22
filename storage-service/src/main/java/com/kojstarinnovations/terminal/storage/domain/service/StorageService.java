package com.kojstarinnovations.terminal.storage.domain.service;

import com.kojstarinnovations.terminal.commons.data.constants.I18nStorageConstants;
import com.kojstarinnovations.terminal.commons.data.helper.UUIDHelper;
import com.kojstarinnovations.terminal.commons.data.log.BaseLog;
import com.kojstarinnovations.terminal.commons.data.transport.commons.GetFileRequest;
import com.kojstarinnovations.terminal.commons.data.payload.commons.StorageResponse;
import com.kojstarinnovations.terminal.commons.exception.BadRequestException;
import com.kojstarinnovations.terminal.commons.exception.NotFoundException;
import com.kojstarinnovations.terminal.storage.domain.opextends.StorageServiceInfoOP;
import com.kojstarinnovations.terminal.storage.domain.ucextends.StorageUC;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.logging.Logger;

/**
 * StorageService - Service class for storage service
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@Service
@RequiredArgsConstructor
public class StorageService implements StorageUC {

    /**
     * Store identification
     *
     * @param file               MultipartFile
     * @param identificationType String
     * @param host               String
     * @return StorageResponse
     * @throws IOException if the file is empty or cannot be stored
     */
    @Override
    public StorageResponse storeIdentification(MultipartFile file, String identificationType, String host) throws IOException {
        subDirectory = "/identification";
        return store(file, host);
    }

    /**
     * Store commodity category
     *
     * @param file MultipartFile
     * @return StorageResponse
     * @throws IOException if the file is empty or cannot be stored
     */
    @Override
    public StorageResponse storeCommodityCategory(MultipartFile file, String host) throws IOException {

        Logger.getLogger("StorageService").info("Saving commodity category image");
        subDirectory = "/commodity-category";
        return store(file, host);
    }

    /**
     * Store brand
     *
     * @param file MultipartFile
     * @return StorageResponse
     * @throws IOException if the file is empty or cannot be stored
     */
    @Override
    public StorageResponse storeBrand(MultipartFile file, String host) throws IOException {
        Logger.getLogger("StorageService").info("Saving brand image");

        subDirectory = "/brand";
        return store(file, host);
    }

    /**
     * Store commodity
     *
     * @param file MultipartFile
     * @return StorageResponse
     * @throws IOException if the file is empty or cannot be stored
     */
    @Override
    public StorageResponse storeCommodity(MultipartFile file, String host) throws IOException {
        Logger.getLogger("StorageService").info("Saving commodity image");

        subDirectory = "/commodity";
        return store(file, host);
    }

    /**
     * Storage store
     *
     * @param file MultipartFile
     * @return StorageResponse
     * @throws IOException if the file is empty or cannot be stored
     */
    @Override
    public StorageResponse storageStore(MultipartFile file, String host) throws IOException {
        Logger.getLogger("StorageService").info("Saving store image");

        subDirectory = "/store";
        return store(file,  host);
    }

    /**
     * Store branch storage
     *
     * @param file MultipartFile
     * @return StorageResponse
     */
    @Override
    public StorageResponse storageStoreBranch(MultipartFile file, String host) throws IOException {
        Logger.getLogger("StorageService").info("Saving store branch image");

        subDirectory = "/store-branch";
        return store(file,  host);
    }

    /**
     * Initialize storage
     */
    @Override
    @PostConstruct
    public void initStorage() throws IOException {
        rootLocation = Paths.get(mediaLocation + subDirectory);
        Files.createDirectories(rootLocation);
    }

    /**
     * Store
     *
     * @param file               MultipartFile
     * @return StorageResponse
     * @throws IOException if the file is empty or cannot be stored
     */
    @Override
    public StorageResponse store(MultipartFile file, String host) throws IOException {

        initStorage();

        String filename = getFilename(file);

        Path destinationFile = this.rootLocation.resolve(
                Paths.get(filename)
        ).normalize().toAbsolutePath();

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);

            StorageResponse response = new StorageResponse();
            response.setFilename(filename);
            response.setFileDownloadUri(
                    ServletUriComponentsBuilder
                            .fromHttpUrl(host)
                            .path("/storage/")
                            .path(filename)
                            .toUriString()
            );
            response.setFileType(file.getContentType());
            response.setSize(file.getSize());
            return response;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    /**
     * Get filename
     *
     * @param file               MultipartFile
     * @return String
     */
    private static String getFilename(MultipartFile file) {
        if (file == null) {
            throw new BadRequestException(I18nStorageConstants.EXCEPTION_FILE_NULL);
        }

        if (file.isEmpty()) {
            throw new BadRequestException(I18nStorageConstants.EXCEPTION_FILE_EMPTY);
        }

        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));

        return createFileName() + fileExtension;
    }

    /**
     * Load as resource
     *
     * @param request name of the file
     * @return Resource
     */
    public Resource loadAsResource(GetFileRequest request) {
        try {
            rootLocation = Paths.get(mediaLocation);
            Path file = rootLocation.resolve(getSubDirectory(request.getCategory())).resolve(request.getFilename()).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + request.getFilename());
            }
        } catch (Exception e) {
            throw new NotFoundException(I18nStorageConstants.EXCEPTION_SOURCE_NOT_FOUND);
        }
    }

    /**
     * Get subdirectory based on category
     *
     * @param category the category
     * @return String
     */
    String getSubDirectory(String category) {
        return switch (category) {
            case "DPI", "NIT", "PASSPORT", "DRIVER_LICENSE", "SOCIAL_SECURITY" -> "identification";
            case "COMMODITY_CATEGORY" -> "commodity-category";
            case "BRAND" -> "brand";
            case "COMMODITY" -> "commodity";
            case "STORE" -> "store";
            case "PROFILE_PICTURE" -> "profile-picture";
            case "STORE_BRANCH" -> "store-branch";
            default -> throw new UnsupportedOperationException("Categoría no soportada");
        };
    }

    /**
     * Load as resource
     *
     * @param filename name of the file
     * @return Resource
     */
    public Resource loadAssetsImg(String filename) {
        try {
            rootLocation = Paths.get(mediaLocation);
            Path file = rootLocation.resolve("assets").resolve(filename);

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {

                storageServiceInfoOP.save(
                        BaseLog.builder()
                                .timestamp(LocalDateTime.now())
                                .userId("System")
                                .eventType("Get Asset")
                                .details(Map.of(
                                        "Service", "StorageService",
                                        "Method", "loadAssetsImg"
                                ))
                                .build()
                );
                return resource;
            } else {
                throw new NotFoundException("Could not read file: " + filename);
            }
        } catch (Exception e) {
            throw new NotFoundException(I18nStorageConstants.EXCEPTION_ASSETS_NOT_FOUND);
        }
    }

    /**
     * Método para crear el nombre del archivo
     *
     * @return Nombre del archivo
     */
    public static String createFileName() {
        String UUID = UUIDHelper.generateUUID("", 5);
        return UUID + "-" + LocalDateTime.now();
    }

    @Value("${app.media.location}")
    private String mediaLocation;
    private String subDirectory = "";
    private Path rootLocation;

    private final StorageServiceInfoOP storageServiceInfoOP;
}