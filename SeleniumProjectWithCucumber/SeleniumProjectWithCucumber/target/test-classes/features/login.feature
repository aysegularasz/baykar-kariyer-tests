@login

Feature: Baykar Kariyer web sitesi

  Scenario: Giris sayfasinin dogru sekilde yuklenmesi
    Given Baykar Kariyer web sitesi acilir
    Then Sayfa dogru sekilde yuklenmelidir

  Scenario: Is ilanlari sayfasinin goruntulenmesi
    Given Kullanici "Tum Ilanlar" sekmesine tiklar
    Then Is ilanları listesi dogru sekilde goruntulenmelidir

  Scenario: Is ilanlari filtresi ile pozisyon secilmesi
    Given Kullanici "Tum Ilanlar" sekmesinde "Pozisyon" filtresi secer
    Then Pozisyona uygun is ilanlari listelenmelidir

  Scenario: Basvuru isleminin basariyla yapilmasi
    Given Kullanici bir ilan secer ve "Basvur" butonuna tiklar
    When Kullanici basvuru formunu doldurur
    And Gonder butonuna tiklar
    Then Basvuru basariyla yapilmis olmalidir

  Scenario: Hata mesajlarinin dogru sekilde gosterilmesi
    Given Kullanici basvuru formunu eksik doldurur
    When Gonder butonuna tiklar
    Then Hata mesajları dogru sekilde goruntulenmelidir