package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lotto.TestConstant;
import lotto.constant.LottoRank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

class WinningLottoTest {
    private final static Lotto lotto = new Lotto(TestConstant.normalNumbers);

    private WinningLotto winningLotto;

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    @Test
    void setBonusNumberByDuplicatedNumbers() {
        init(TestConstant.normalNumbers);

        assertThatThrownBy(() -> winningLotto.setBonusNumber(1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호를 여러 번 할당 하려고 하면 예외가 발생한다.")
    @Test
    void setBonusNumberMultipleTimes() {
        init(TestConstant.normalNumbers);
        winningLotto.setBonusNumber(45);

        assertThatThrownBy(() -> winningLotto.setBonusNumber(45))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("보너스 번호를 할당한다.")
    @Test
    void setBonusNumber() {
        init(TestConstant.normalNumbers);
        winningLotto.setBonusNumber(45);
    }

    @DisplayName("보너스 번호를 할당하기 전 당첨 등수를 계산하려 하면 예외가 발생한다.")
    @Test
    void calculateRankBeforeSetBonusNumber() {
        init(TestConstant.normalNumbers);

        assertThatThrownBy(() -> winningLotto.calculateRank(lotto))
                .isInstanceOf(IllegalStateException.class);
    }


    @DisplayName("당첨 등수를 계산한다. - 6개 일치")
    @Test
    void calculateRankWhenSixMatched() {
        init(TestConstant.normalNumbers);
        winningLotto.setBonusNumber(45);

        LottoRank lottoRank = winningLotto.calculateRank(lotto);

        assertThat(lottoRank).isEqualTo(LottoRank.SIX_MATCH);
    }

    @DisplayName("당첨 등수를 계산한다. - 5개 일치, 보너스 번호 일치")
    @Test
    void calculateRankWhenFiveAndBonusMatched() {
        init(List.of(1, 2, 3, 4, 5, 45));
        winningLotto.setBonusNumber(45);

        LottoRank lottoRank = winningLotto.calculateRank(lotto);

        assertThat(lottoRank).isEqualTo(LottoRank.FIVE_AND_BONUS_MATCH);
    }

    @DisplayName("당첨 등수를 계산한다. - 5개 일치")
    @Test
    void calculateRankWhenFiveMatched() {
        init(List.of(1, 2, 3, 4, 5, 45));
        winningLotto.setBonusNumber(40);

        LottoRank lottoRank = winningLotto.calculateRank(lotto);

        assertThat(lottoRank).isEqualTo(LottoRank.FIVE_MATCH);
    }

    @DisplayName("당첨 등수를 계산한다. - 4개 일치")
    @Test
    void calculateRankWhenFourMatched() {
        init(List.of(1, 2, 3, 4, 44, 45));
        winningLotto.setBonusNumber(45);

        LottoRank lottoRank = winningLotto.calculateRank(lotto);

        assertThat(lottoRank).isEqualTo(LottoRank.FOUR_MATCH);
    }

    @DisplayName("당첨 등수를 계산한다. - 3개 일치")
    @Test
    void calculateRankWhenThreeMatched() {
        init(List.of(1, 2, 3, 43, 44, 45));
        winningLotto.setBonusNumber(45);

        LottoRank lottoRank = winningLotto.calculateRank(lotto);

        assertThat(lottoRank).isEqualTo(LottoRank.THREE_MATCH);
    }

    @DisplayName("당첨 등수를 계산한다. - 2개 이하 일치")
    @Test
    void calculateRankWhenUnderTwoMatched() {
        init(TestConstant.normalNumbers);
        winningLotto.setBonusNumber(45);
        Lotto noMatchedLotto = new Lotto(List.of(40, 41, 42, 43, 44, 45));
        Lotto oneMatchedLotto = new Lotto(List.of(1, 41, 42, 43, 44, 45));
        Lotto twoMatchedLotto = new Lotto(List.of(1, 2, 42, 43, 44, 45));

        LottoRank lottoRank1 = winningLotto.calculateRank(noMatchedLotto);
        LottoRank lottoRank2 = winningLotto.calculateRank(oneMatchedLotto);
        LottoRank lottoRank3 = winningLotto.calculateRank(twoMatchedLotto);

        assertThat(lottoRank1).isEqualTo(LottoRank.OUT_RANK);
        assertThat(lottoRank2).isEqualTo(LottoRank.OUT_RANK);
        assertThat(lottoRank3).isEqualTo(LottoRank.OUT_RANK);
    }

    private void init(List<Integer> numbers) {
        Lotto lotto = new Lotto(numbers);
        winningLotto = new WinningLotto(lotto);
    }
}