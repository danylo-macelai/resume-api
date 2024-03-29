<div align="justify">
    <h3>Guia Simples para Configurar o Ambiente de Desenvolvimento no Eclipse</h3>
    <p>
        Este guia explica de forma simples como configurar o Eclipse para desenvolver um projeto, incluindo a importação de um repositório e execução do projeto. Siga estas instruções para garantir que o ambiente esteja pronto para o desenvolvimento.
    </p>
    <p><strong>Pré-requisitos:</strong></p>
    <p>
        Certifique-se de que já clonou o repositório do projeto e configurou o ambiente seguindo as instruções iniciais. Se ainda não fez isso, consulte a seção <a href="../README.md#6-como-executar-o-projeto">6</a> do <strong>README</strong> para instruções de clonagem, build e execução do projeto.
        </p>
    <blockquote>
        <p>⚠️ <strong>Atenção:</strong></p>
        <p>
            Siga todas as etapas para que o ambiente esteja corretamente configurado e sem erros.
        </p>
    </blockquote>
    <h4>Como Importar o Repositório no Eclipse</h4>
    <p>
        Abra o Eclipse. Se não tiver um workspace configurado, escolha um local. Informe o caminho onde o repositório foi clonado, como <code>C:\WORKSPACES\RESUME\RESUME-API\</code>, de acordo com a seção <a href="../README.md#6-como-executar-o-projeto">6</a> do <strong>README</strong>.
    </p>
    <blockquote>
        <p>💡 <strong>Dica:</strong></p>
        <p>
            Se ainda não instalou o Eclipse, baixe-o <a href="https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/2024-09/R/eclipse-jee-2024-09-R-win32-x86_64.zip" target="_blank">aqui</a> e siga as instruções de instalação.
        </p>
    </blockquote>
    <h4>Importando o Repositório Já Clonado</h4>
    <p>
        No Eclipse, vá em <code>File &gt; Import...</code>. Em <strong>Git</strong>, selecione <strong>Projects from Git (with smart import)</strong> e clique em <code>Next &gt;</code>. Escolha <strong>Existing local repository</strong> e clique em <code>Next &gt;</code>. Em seguida, clique em <strong>Add...</strong>.
    </p>
    <div align="center">
        <picture>
            <img alt="eclipse-import-projects-from-git.png" src="./eclipse-import-projects-from-git.png" width="800px" height="370px" loading="lazy" />
        </picture>
        <p><sup>Import Projects from Git</sup></p>
    </div>
    <p>
        Selecione o repositório em <code>C:\WORKSPACES\RESUME\RESUME-API\resume-api</code>, marque a caixa ao lado do repositório e clique em <strong>Add</strong>.
    </p>
    <div align="center">
        <picture>
            <img alt="eclipse-add-git-repositories.png" src="./eclipse-add-git-repositories.png" width="584px" height="420px" loading="lazy" />
        </picture>
        <p><sup>Add Git Repositories</sup></p>
    </div>
    <h4>Importar o Projeto no Eclipse</h4>
    <p>
        Vá novamente em <code>File > Import...</code>, expanda <strong>General</strong> e selecione <strong>Existing Projects into Workspace</strong>. Clique em <code>Next ></code>.
    </p>
    <div align="center">
        <picture>
            <img alt="eclipse-import-projects-m.png" src="./eclipse-import-projects-m.png" width="470px" height="370px" loading="lazy" />
        </picture>
        <p><sup>Import Projects</sup></p>
    </div>
    <p>
        Selecione o diretório do projeto em <code>C:\WORKSPACES\RESUME\RESUME-API\resume-api</code>. Na seção <strong>Projects</strong>, marque <strong>resume-api</strong> e clique em <code>Finish</code>.
    </p>
    <div align="center">
        <picture>
            <img alt="eclipse-import-projects.png" src="./eclipse-import-projects.png" width="584px" height="420px" loading="lazy" />
        </picture>
        <p><sup>Eclipse Import Projects</sup></p>
    </div>
    <h4>Importando Preferências de Estilo de Código</h4>
    <p>
        Vá em <code>File > Import...</code>, expanda <strong>General</strong> e escolha <strong>Preferences</strong>. Clique em <code>Next ></code>.
    </p>
    <div align="center">
        <picture>
            <img alt="eclipse-import-preferences-m.png" src="./eclipse-import-preferences-m.png" width="470px" height="370px" loading="lazy" />
        </picture>
        <p><sup>Import Preferences</sup></p>
    </div>
    <p>
        Clique em <strong>Browse...</strong> e encontre o arquivo de preferências em <code>C:\WORKSPACES\RESUME\RESUME-API\resume-api\.github\docs\resume_preferences.epf</code>. Selecione o arquivo, marque <strong>Import all</strong> e clique em <code>Finish</code>.
    </p>
    <div align="center">
        <picture>
            <img alt="eclipse-import-preferences.png" src="./eclipse-import-preferences.png" width="584px" height="420px" loading="lazy" />
        </picture>
        <p><sup>Import Preferences</sup></p>
    </div>
    <blockquote>
        <p>⚠️ <strong>Atenção:</strong></p>
        <p>
            Após importar, o Eclipse pedirá para reiniciar. Clique em <strong>Restart</strong> para aplicar as configurações.
        </p>
    </blockquote>
    <h4>Importando Configurações de Launch</h4>
    <p>
        Vá em <code>File > Import...</code> e escolha <strong>General > Run/Debug > Launch Configurations</strong>, depois clique em <code>Next ></code>.
    </p>
    <div align="center">
        <picture>
            <img alt="eclipse-import-launch-m.png" src="./eclipse-import-launch-m.png" width="470px" height="370px" loading="lazy" />
        </picture>
        <p><sup>Import Launch</sup></p>
    </div>
    <p>
        Clique em <strong>Browse...</strong>, selecione o arquivo de configuração em <code>C:\WORKSPACES\RESUME\RESUME-API\resume-api\.github\docs\run</code>. Marque as configurações desejadas e clique em <strong>Finish</strong>.
    </p>
    <div align="center">
        <picture>
            <img alt="eclipse-import-launch.png" src="./eclipse-import-launch.png" width="584px" height="420px" loading="lazy" />
        </picture>
        <p><sup>Import Launch</sup></p>
    </div>
    <h4>Executando o Projeto no Eclipse</h4>
    <p>
        Para executar o projeto, vá em <strong>Run</strong>, depois em <strong>Run As &gt; Debug Configurations...</strong>. Na janela de configurações, expanda <strong>Java Application</strong>, selecione <strong>ResumeApplication</strong> e clique em <code>Debug</code>.
    </p>
    <blockquote>
        <p>💡 <strong>Dica:</strong></p>
        <p>
            Na aba Environment, configure as credenciais do banco H2 em <code>DATASOURCE_USERNAME</code> e <code>DATASOURCE_PASSWORD</code>.
        </p>
    </blockquote>
    <div align="center">
        <picture>
            <img alt="eclipse-debug-configurations.png" src="./eclipse-debug-configurations.png" width="900px" height="360px" loading="lazy" />
        </picture>
        <p><sup>Debug Configurations</sup></p>
    </div>
    <p>Seguindo essas etapas, o projeto deve estar pronto para rodar sem problemas no Eclipse.</p>
</div>