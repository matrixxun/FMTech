package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface DocDetails
{
  public static final class DocumentDetails
    extends MessageNano
  {
    public AlbumDetails albumDetails = null;
    public AppDetails appDetails = null;
    public ArtistDetails artistDetails = null;
    public BookDetails bookDetails = null;
    public BookSeriesDetails bookSeriesDetails = null;
    public DeveloperDetails developerDetails = null;
    public MagazineDetails magazineDetails = null;
    public DocDetails.PersonDetails personDetails = null;
    public SongDetails songDetails = null;
    public SubscriptionDetails subscriptionDetails = null;
    public DocDetails.TalentDetails talentDetails = null;
    public TvEpisodeDetails tvEpisodeDetails = null;
    public TvSeasonDetails tvSeasonDetails = null;
    public TvShowDetails tvShowDetails = null;
    public VideoDetails videoDetails = null;
    
    public DocumentDetails()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.appDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.appDetails);
      }
      if (this.albumDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.albumDetails);
      }
      if (this.artistDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.artistDetails);
      }
      if (this.songDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.songDetails);
      }
      if (this.bookDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.bookDetails);
      }
      if (this.videoDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.videoDetails);
      }
      if (this.subscriptionDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.subscriptionDetails);
      }
      if (this.magazineDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(8, this.magazineDetails);
      }
      if (this.tvShowDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(9, this.tvShowDetails);
      }
      if (this.tvSeasonDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(10, this.tvSeasonDetails);
      }
      if (this.tvEpisodeDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(11, this.tvEpisodeDetails);
      }
      if (this.personDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(12, this.personDetails);
      }
      if (this.talentDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(13, this.talentDetails);
      }
      if (this.developerDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(14, this.developerDetails);
      }
      if (this.bookSeriesDetails != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(15, this.bookSeriesDetails);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.appDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.appDetails);
      }
      if (this.albumDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.albumDetails);
      }
      if (this.artistDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.artistDetails);
      }
      if (this.songDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.songDetails);
      }
      if (this.bookDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.bookDetails);
      }
      if (this.videoDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.videoDetails);
      }
      if (this.subscriptionDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.subscriptionDetails);
      }
      if (this.magazineDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(8, this.magazineDetails);
      }
      if (this.tvShowDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(9, this.tvShowDetails);
      }
      if (this.tvSeasonDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(10, this.tvSeasonDetails);
      }
      if (this.tvEpisodeDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(11, this.tvEpisodeDetails);
      }
      if (this.personDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(12, this.personDetails);
      }
      if (this.talentDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(13, this.talentDetails);
      }
      if (this.developerDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(14, this.developerDetails);
      }
      if (this.bookSeriesDetails != null) {
        paramCodedOutputByteBufferNano.writeMessage(15, this.bookSeriesDetails);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PersonDetails
    extends MessageNano
  {
    public boolean hasIsGplusUser = false;
    public boolean hasPersonIsRequester = false;
    public boolean isGplusUser = true;
    public boolean personIsRequester = false;
    
    public PersonDetails()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasPersonIsRequester) || (this.personIsRequester)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
      }
      if ((this.hasIsGplusUser) || (this.isGplusUser != true)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasPersonIsRequester) || (this.personIsRequester)) {
        paramCodedOutputByteBufferNano.writeBool(1, this.personIsRequester);
      }
      if ((this.hasIsGplusUser) || (this.isGplusUser != true)) {
        paramCodedOutputByteBufferNano.writeBool(2, this.isGplusUser);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ProductDetails
    extends MessageNano
  {
    public boolean hasTitle = false;
    public DocDetails.ProductDetailsSection[] section = DocDetails.ProductDetailsSection.emptyArray();
    public String title = "";
    
    public ProductDetails()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.section != null) && (this.section.length > 0)) {
        for (int j = 0; j < this.section.length; j++)
        {
          DocDetails.ProductDetailsSection localProductDetailsSection = this.section[j];
          if (localProductDetailsSection != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, localProductDetailsSection);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.section != null) && (this.section.length > 0)) {
        for (int i = 0; i < this.section.length; i++)
        {
          DocDetails.ProductDetailsSection localProductDetailsSection = this.section[i];
          if (localProductDetailsSection != null) {
            paramCodedOutputByteBufferNano.writeMessage(2, localProductDetailsSection);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ProductDetailsDescription
    extends MessageNano
  {
    private static volatile ProductDetailsDescription[] _emptyArray;
    public String description = "";
    public boolean hasDescription = false;
    public Common.Image image = null;
    
    public ProductDetailsDescription()
    {
      this.cachedSize = -1;
    }
    
    public static ProductDetailsDescription[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new ProductDetailsDescription[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.image != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.image);
      }
      if ((this.hasDescription) || (!this.description.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.description);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.image != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.image);
      }
      if ((this.hasDescription) || (!this.description.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.description);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ProductDetailsSection
    extends MessageNano
  {
    private static volatile ProductDetailsSection[] _emptyArray;
    public DocDetails.ProductDetailsDescription[] description = DocDetails.ProductDetailsDescription.emptyArray();
    public boolean hasTitle = false;
    public String title = "";
    
    public ProductDetailsSection()
    {
      this.cachedSize = -1;
    }
    
    public static ProductDetailsSection[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new ProductDetailsSection[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.title);
      }
      if ((this.description != null) && (this.description.length > 0)) {
        for (int j = 0; j < this.description.length; j++)
        {
          DocDetails.ProductDetailsDescription localProductDetailsDescription = this.description[j];
          if (localProductDetailsDescription != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localProductDetailsDescription);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(1, this.title);
      }
      if ((this.description != null) && (this.description.length > 0)) {
        for (int i = 0; i < this.description.length; i++)
        {
          DocDetails.ProductDetailsDescription localProductDetailsDescription = this.description[i];
          if (localProductDetailsDescription != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localProductDetailsDescription);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class TalentDetails
    extends MessageNano
  {
    public DocDetails.TalentExternalLinks externalLinks = null;
    public boolean hasPrimaryRoleId = false;
    public int primaryRoleId = 0;
    
    public TalentDetails()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.externalLinks != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.externalLinks);
      }
      if ((this.primaryRoleId != 0) || (this.hasPrimaryRoleId)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.primaryRoleId);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.externalLinks != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.externalLinks);
      }
      if ((this.primaryRoleId != 0) || (this.hasPrimaryRoleId)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.primaryRoleId);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class TalentExternalLinks
    extends MessageNano
  {
    public Link googlePlusProfileUrl = null;
    public Link[] websiteUrl = Link.emptyArray();
    public Link youtubeChannelUrl = null;
    
    public TalentExternalLinks()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.websiteUrl != null) && (this.websiteUrl.length > 0)) {
        for (int j = 0; j < this.websiteUrl.length; j++)
        {
          Link localLink = this.websiteUrl[j];
          if (localLink != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localLink);
          }
        }
      }
      if (this.googlePlusProfileUrl != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.googlePlusProfileUrl);
      }
      if (this.youtubeChannelUrl != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.youtubeChannelUrl);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.websiteUrl != null) && (this.websiteUrl.length > 0)) {
        for (int i = 0; i < this.websiteUrl.length; i++)
        {
          Link localLink = this.websiteUrl[i];
          if (localLink != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localLink);
          }
        }
      }
      if (this.googlePlusProfileUrl != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.googlePlusProfileUrl);
      }
      if (this.youtubeChannelUrl != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.youtubeChannelUrl);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.DocDetails
 * JD-Core Version:    0.7.0.1
 */