package com.hrproject.hrwebsiteproject.util;

import com.hrproject.hrwebsiteproject.model.entity.StaticContent;
import com.hrproject.hrwebsiteproject.repository.StaticContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StaticContentDataInitializer implements CommandLineRunner {
    private final StaticContentRepository staticContentRepository;

    @Override
    public void run(String... args) {
        if (staticContentRepository.count() == 0) {
            staticContentRepository.saveAll(List.of(
                    StaticContent.builder()
                            .key("homepage")
                            .title("Ana Sayfa")
                            .content("İK süreçlerinizi kolaylaştıran dijital çözüm!")
                            .build(),
                    StaticContent.builder()
                            .key("how-it-works")
                            .title("Nasıl Çalışır")
                            .content("Kayıt olun, şirketinizi oluşturun ve personel ekleyin. Süreç bu kadar basit!")
                            .build(),
                    StaticContent.builder()
                            .key("platform-features")
                            .title("Platform Özellikleri")
                            .content("İzin yönetimi, maaş hesaplama, performans değerlendirme ve daha fazlası.")
                            .build(),
                    StaticContent.builder()
                            .key("holidays")
                            .title("Resmi Tatiller")
                            .content("""
                                01.01.2025 Yılbaşı
                                20.04.2025 Ramazan Bayramı (1. Gün)
                                21.04.2025 Ramazan Bayramı (2. Gün)
                                22.04.2025 Ramazan Bayramı (3. Gün)
                                23.04.2025 Ulusal Egemenlik ve Çocuk Bayramı
                                01.05.2025 Emek ve Dayanışma Günü
                                19.05.2025 Atatürk'ü Anma, Gençlik ve Spor Bayramı
                                15.06.2025 Kurban Bayramı (Arefe)
                                16.06.2025 Kurban Bayramı (1. Gün)
                                17.06.2025 Kurban Bayramı (2. Gün)
                                18.06.2025 Kurban Bayramı (3. Gün)
                                19.06.2025 Kurban Bayramı (4. Gün)
                                15.07.2025 Demokrasi ve Millî Birlik Günü
                                30.08.2025 Zafer Bayramı
                                29.10.2025 Cumhuriyet Bayramı
                                """)
                            .build()
            ));
            System.out.println("🟢 StaticContent tablosuna örnek veriler eklendi.");
        }
    }
}
